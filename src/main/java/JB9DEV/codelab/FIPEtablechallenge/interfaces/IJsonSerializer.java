package JB9DEV.codelab.FIPEtablechallenge.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface IJsonSerializer {
    <T> T deserialize(String json, Class<T> classBluePrint);
    <T> T deserialize(String list, TypeReference<T> listType);
}
