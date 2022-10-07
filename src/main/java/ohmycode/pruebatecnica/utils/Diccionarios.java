package ohmycode.pruebatecnica.utils;

import java.util.HashMap;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class Diccionarios {
	HashMap<String,Direction> sort = new HashMap<String,Direction>() {{
	    put("desc", Direction.DESC);
	    put("asc", Direction.ASC);
	}};

	public HashMap<String, Direction> getSort() {
		return sort;
	}	
}
