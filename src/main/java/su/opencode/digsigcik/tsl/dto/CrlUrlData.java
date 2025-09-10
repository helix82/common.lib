package su.opencode.digsigcik.tsl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrlUrlData {
    @JsonProperty("caKeyId")
    private String caKeyId;
    @JsonProperty("url")
    private String url;

    public static CrlUrlData of(String url) {

        CrlUrlData result = new CrlUrlData();
        result.setUrl(url);
        return result;
    }

    public static CrlUrlData of(String caKeyId, String url) {

        CrlUrlData result = new CrlUrlData();
        result.setCaKeyId(caKeyId);
        result.setUrl(url);
        return result;
    }

    public static List<CrlUrlData> of(CAXml.CA.SHSystems.SHSystem.KeysAuthPersons.KeyAuthPerson ca) {

        List<CrlUrlData> entities = new ArrayList<>();

        for (var addresses : ca.getAddresses())
            for (var address : addresses.getAddress())
                entities.add(CrlUrlData.of(ca.getKeyId(), address));

        return entities;
    }
}
