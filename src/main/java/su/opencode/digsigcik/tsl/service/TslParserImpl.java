package su.opencode.digsigcik.tsl.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import su.opencode.digsigcik.tsl.dto.CAXml;
import su.opencode.digsigcik.tsl.dto.CertificateData;
import su.opencode.digsigcik.tsl.dto.CrlUrlData;
import su.opencode.digsigcik.tsl.dto.ParsedCaData;
import su.opencode.digsigcik.tsl.util.CAXmlUtil;
import su.opencode.digsigcik.tsl.util.XMLUtil;

import javax.xml.bind.JAXBException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static su.opencode.digsigcik.Constants.*;
import static su.opencode.digsigcik.tsl.util.XMLUtil.convertStringToDocument;

@Service
public class TslParserImpl implements TslParser {

    @Override
    public ParsedCaData parseTsl(String xmlContent) throws JAXBException {
        Document document = convertStringToDocument(xmlContent);
        assert document != null;
        CAXml caXml = XMLUtil.unmarshal(document, CAXml.class);

        if (caXml == null) {
            throw new IllegalArgumentException(CA_IS_NULL);
        }

        List<CertificateData> certificates = new ArrayList<>();
        List<CrlUrlData> crlUrls = new ArrayList<>();
        Set<String> uniqueDomains = new HashSet<>();

        for (CAXml.CA ca : caXml.getCa()) {
            String stat = ca.getAccreditationStatusCode(ca.getAccreditationStatus().getStatus());
            if (VALID_STATUS.equals(stat) && ca.getShSystems().getShSystem() != null) {
                List<CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson> keyAuthPersons =
                        ca.getShSystems().getShSystem()
                                .stream()
                                .flatMap(sh -> sh.getKeysAuthPersons().getKeyAuthPerson().stream())
                                .collect(Collectors.toList());

                for (var keyAP : keyAuthPersons) {
                    CAXmlUtil.mergeDomainAddresses(uniqueDomains, keyAP);
                    certificates.addAll(extractCertificates(keyAP, stat));
                    crlUrls.addAll(extractCrlUrls(keyAP));
                }
            }
        }

        return new ParsedCaData(certificates, crlUrls, uniqueDomains);
    }

    private List<CertificateData> extractCertificates(
            CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson keyAP, String stat) {
        List<CertificateData> result = new ArrayList<>();
        List<CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson.Certificates.Certificate> certData =
                keyAP.getCertificates().getCaCertificateData();

        for (var cert : certData) {
            LocalDateTime dateStart = LocalDateTime.parse(cert.getDateStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            LocalDateTime dateEnd = LocalDateTime.parse(cert.getDateEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            LocalDateTime currDateTime = LocalDateTime.now();

            if (!currDateTime.isBefore(dateStart) && !currDateTime.isAfter(dateEnd)) {
                String issuedTo = cert.getIssuedTo();
                var substr = issuedTo.split(", ");
                for (String s : substr) {
                    if (s.startsWith("CN=")) {
                        issuedTo = s.substring(3);
                        break;
                    }
                }

                CertificateData certDto = new CertificateData();
                certDto.setThumbprint(cert.getThumbprint());
                certDto.setSerialNumber(cert.getSerialNumber());
                certDto.setDateStart(dateStart);
                certDto.setDateEnd(dateEnd);
                certDto.setIssuedTo(issuedTo);
                certDto.setCertificate(cert.getCertificate());
                certDto.setKeyId(keyAP.getKeyId());
                certDto.setAccreditationStatus(stat);
                certDto.setTypeInput(CONTROLLER_CA_TYPE_INPUT_AUTO);

                result.add(certDto);
            }
        }

        return result;
    }

    private List<CrlUrlData> extractCrlUrls(
            CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson keyAP) {
        return CrlUrlData.of(keyAP).stream()
                .map(entity -> new CrlUrlData(entity.getCaKeyId(), entity.getUrl()))
                .collect(Collectors.toList());
    }
}
