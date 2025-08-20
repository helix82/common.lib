package su.opencode.digsigcik.ucfk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CertificateInfo {

    // TODO: validation, xml obj, openAPI unmarshalling

    @JsonProperty("SubjectKeyId")
    private String subjectKeyId;

    @JsonProperty("FormedDisplay")
    private String formedDisplay;

    @JsonProperty("SerialNumber")
    private String serialNumber;

    @JsonProperty("Type")
    private int type;

    @JsonProperty("ValidFrom")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String validFrom;

    @JsonProperty("ValidFromDisplay")
    private String validFromDisplay;

    @JsonProperty("ValidTo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String validTo;

    @JsonProperty("ValidToDisplay")
    private String validToDisplay;

    @JsonProperty("LastName")
    private String lastName;

    @JsonProperty("FirstName")
    private String firstName;

    @JsonProperty("Surname")
    private String surname;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("CountryCode")
//    @Size(min = 2, max = 2)
    private String countryCode;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("Locality")
    private String locality;

    @JsonProperty("OrganizationName")
    private String organizationName;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Subdivision")
    private String subdivision;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("PositionCode")
    private String positionCode;

    @JsonProperty("PositionFormalized")
    private String positionFormalized;

    @JsonProperty("AdditionalFields")
    private String additionalFields;

    @JsonProperty("CertificateAdditionalFields")
    private Map<String, String> certificateAdditionalFields;

    @JsonProperty("Ogrn")
    private String ogrn;

    @JsonProperty("OgrnIp")
    private String ogrnIp;

    @JsonProperty("Inn")
//    @Size(min = 10, max = 12)
    private String inn;

    @JsonProperty("Snils")
//    @Size(min = 11, max = 11)
    private String snils;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("ExtendedKeyUsage")
    private String extendedKeyUsage;

    @JsonProperty("Powers")
    private String powers;

    @JsonProperty("AuthorityCenterName")
    private String authorityCenterName;

    @JsonProperty("AuthorityCenterCountry")
    private String authorityCenterCountry;

    @JsonProperty("AuthorityCenterCountryName")
    private String authorityCenterCountryName;

    @JsonProperty("AuthorityCenterState")
    private String authorityCenterState;

    @JsonProperty("AuthorityCenterLocality")
    private String authorityCenterLocality;

    @JsonProperty("AuthorityCenterStreetAddress")
    private String authorityCenterStreetAddress;

    @JsonProperty("AuthorityCenterAddress")
    private String authorityCenterAddress;

    @JsonProperty("AuthorityCertSerialNumber")
    private String authorityCertSerialNumber;

    @JsonProperty("SignTool")
    private String signTool;

    @JsonProperty("SignToolCert")
    private String signToolCert;

    @JsonProperty("CaTool")
    private String caTool;

    @JsonProperty("CaToolCert")
    private String caToolCert;

    @JsonProperty("AuthorityCenterPolicyClass")
    private String authorityCenterPolicyClass;

    @JsonProperty("Algorithm")
    private String algorithm;

    @JsonProperty("KeyUsage")
    private String keyUsage;

    @JsonProperty("SubjectPublicKey")
    private String subjectPublicKey;

    @JsonProperty("SubjectPublicKeyDisplay")
    private String subjectPublicKeyDisplay;

    @JsonProperty("AlgorithmIdentifier")
    private String algorithmIdentifier;

    @JsonProperty("Encrypted")
    private String encrypted;

    @JsonProperty("EncryptedDisplay")
    private String encryptedDisplay;

    @JsonProperty("IsApproved")
    private boolean isApproved;

    @JsonProperty("CommonName")
    private String commonName;
}
