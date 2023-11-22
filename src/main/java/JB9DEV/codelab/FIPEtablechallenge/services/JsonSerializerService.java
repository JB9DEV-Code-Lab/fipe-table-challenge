package JB9DEV.codelab.FIPEtablechallenge.services;

import JB9DEV.codelab.FIPEtablechallenge.exceptions.JsonSerializerException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonSerializerService implements IJsonSerializer {
    private final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    public <T> T deserialize(String json, Class<T> classBluePrint) {
        try {
            return MAPPER.readValue(json, classBluePrint);
        } catch (JsonProcessingException exception) {
            throw new JsonSerializerException(
                    "Couldn't deserialize %s due to %s".formatted(json, exception.getMessage())
            );
        }
    }

    public <T> T deserialize(String list, TypeReference<T> listType) {
        try {
            return MAPPER.readValue(list, listType);
        } catch (JsonProcessingException exception) {
            throw new JsonSerializerException(
                    "Couldn't deserialize %s due to %s".formatted(list, exception.getMessage())
            );
        }
    }
}
