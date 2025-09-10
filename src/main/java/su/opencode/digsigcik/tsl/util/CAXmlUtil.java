package su.opencode.digsigcik.tsl.util;

import lombok.experimental.UtilityClass;
import su.opencode.digsigcik.tsl.dto.CAXml;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@UtilityClass
public class CAXmlUtil {
    public static void mergeDomainAddresses(Collection<String> collector, CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson keyAP) {
        keyAP.getAddresses().forEach(addresses -> {
            collector.addAll(addresses.getAddress().stream().map(CAXmlUtil::extractDomainOnly).collect(Collectors.toList()));
        });
    }

    public static String extractDomainOnly(String url) {
        return URI.create(url).getHost();
    }
}
