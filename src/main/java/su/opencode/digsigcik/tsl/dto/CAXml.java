package su.opencode.digsigcik.tsl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.opencode.digsigcik.audit.AllowAudit;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import java.util.List;

import static su.opencode.digsigcik.Constants.*;

@NoArgsConstructor
@Data
@XmlRootElement(name = XML_ELEMENT_ACC)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = XML_ELEMENT_ACC)
public class CAXml {
    @XmlElement(name = XML_ELEMENT_CA)
    public List<CA> ca;

    @Data
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CA {
        @XmlElement(name = XML_ELEMENT_SHC)
        public SHSystems shSystems;
        @XmlElement(name = XML_ELEMENT_ACCREDITATION_STATUS)
        private AccreditationStatus accreditationStatus;

        @Data
        @XmlRootElement
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class SHSystems {
            @XmlElement(name = XML_ELEMENT_SH_SYSTEM)
            public List<SHSystem> shSystem;

            @Data
            @XmlRootElement
            @XmlAccessorType(XmlAccessType.FIELD)
            public static class SHSystem {
                @XmlElement(name = XML_ELEMENT_KEYS_AUTH_PERSONS)
                private KeysAuthPersons keysAuthPersons;

                @Data
                @XmlRootElement
                @XmlAccessorType(XmlAccessType.FIELD)
                public static class KeysAuthPersons {
                    @XmlElement(name = XML_ELEMENT_KEY)
                    private List<KeyAuthPerson> keyAuthPerson;

                    @Data
                    @XmlRootElement
                    @XmlAccessorType(XmlAccessType.FIELD)
                    public static class KeyAuthPerson {
                        @XmlElement(name = XML_ELEMENT_KEY_ID)
                        private String keyId;
                        @XmlElement(name = XML_ELEMENT_REVIEW_LIST_ADDRESS)
                        private List<Addresses> addresses;
                        @XmlElement(name = XML_ELEMENT_CERTIFICATES)
                        private Certificates certificates;

                        @Data
                        @XmlRootElement
                        @XmlAccessorType(XmlAccessType.FIELD)
                        public static class Addresses {
                            @XmlElement(name = XML_ELEMENT_ADDRESS)
                            private List<String> address;
                        }

                        @Data
                        @XmlRootElement
                        @XmlAccessorType(XmlAccessType.FIELD)
                        public static class Certificates {
                            @XmlElement(name = XML_ELEMENT_CERTIFICATE_DATA)
                            private List<Certificate> caCertificateData;

                            @Data
                            @XmlRootElement
                            @XmlAccessorType(XmlAccessType.FIELD)
                            public static class Certificate {

                                @AllowAudit
                                @Schema(example = SCHEMA_CA_ACCREDITATION_STATUS_EXAMPLE)
                                @Column(name = "accreditation_status")
                                @JsonProperty("accreditationStatus")
                                private String accreditationStatus;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_SERIAL_NUMBER)
                                @Column(name = "serial_number")
                                @JsonProperty("serialNumber")
                                private String serialNumber;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_DATE_START)
                                @Column(name = "date_start")
                                @JsonProperty("dateStart")
                                private String dateStart;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_DATE_END)
                                @Column(name = "date_end")
                                @JsonProperty("dateEnd")
                                private String dateEnd;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_IMPRINT)
                                @Column(name = "thumbprint")
                                @JsonProperty("thumbprint")
                                private String thumbprint;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_KEY_ID)
                                @Column(name = "key_id")
                                @JsonProperty("keyId")
                                private String keyId;

                                @AllowAudit
                                @XmlElement(name = XML_ELEMENT_ISSUED_TO)
                                @Column(name = "issued_to")
                                @JsonProperty("issuedTo")
                                private String issuedTo;

                                @AllowAudit
                                @Schema(example = SCHEMA_CA_TYPE_INPUT_EXAMPLE)
                                @Column(name = "type_input")
                                @JsonProperty("typeInput")
                                private String typeInput;

                                @Column(name = "file_ref")
                                @JsonProperty("fileRef")
                                private Long fileRef;

                                @Transient
                                @XmlElement(name = XML_ELEMENT_CERT)
                                private String certificate;
                            }

                        }
                    }
                }
            }
        }

        @Data
        @XmlRootElement
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class AccreditationStatus {
            @XmlElement(name = XML_ELEMENT_STATUS)
            private String status;
        }

        public String getAccreditationStatusCode(String status) {
            switch (status) {
                case "Действует":
                    return "VALID";
                case "Прекращена":
                    return "DISCONTINUED";
                case "Приостановлена":
                    return "SUSPENDED";
                case "Аннулирована":
                    return "CANCELED";
                default:
                    return null;
            }
        }
    }
}
