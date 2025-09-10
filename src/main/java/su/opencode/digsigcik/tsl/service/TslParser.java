package su.opencode.digsigcik.tsl.service;

//import jakarta.xml.bind.JAXBException;
import su.opencode.digsigcik.tsl.dto.ParsedCaData;

import javax.xml.bind.JAXBException;

public interface TslParser {
    ParsedCaData parseTsl(String xmlContent) throws JAXBException;
}
