package br.com.sapucaia.omit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OmitFieldService {
	
	public static HashMap<String, Object> getFields(Object obj, Class<Annotation> annotationClassToIgnore) throws IllegalAccessException {
	    HashMap<String, Object> fieldsMap = new HashMap<>();

	    // obter todos os campos declarados na classe do objeto, incluindo campos privados
	    Field[] fields = obj.getClass().getDeclaredFields();

	    for (Field field : fields) {
	        // obter o nome do campo
	        String fieldName = field.getName();

	        // verificar se o campo tem a anotação que deve ser ignorada
	        if (field.isAnnotationPresent(annotationClassToIgnore)) {
	            continue;
	        }

	        // tornar o campo acessível para ler seu valor (no caso de ser privado)
	        field.setAccessible(true);

	        // obter o valor do campo no objeto
	        Object fieldValue = field.get(obj);

	        // adicionar o nome e valor do campo no mapa
	        fieldsMap.put(fieldName, fieldValue);
	    }

	    return fieldsMap;
	}
	
	public static <T> T createObjectFromMap(Class<T> clazz, Map<String, Object> map) throws Exception {
	    T obj = clazz.getDeclaredConstructor().newInstance();

	    for (Map.Entry<String, Object> entry : map.entrySet()) {
	        String fieldName = entry.getKey();
	        Object fieldValue = entry.getValue();

	        // obter o campo correspondente na classe
	        Field field = clazz.getDeclaredField(fieldName);

	        // tornar o campo acessível para atribuir seu valor (no caso de ser privado)
	        field.setAccessible(true);

	        // atribuir o valor do campo no objeto
	        field.set(obj, fieldValue);
	    }

	    return obj;
	}
}
