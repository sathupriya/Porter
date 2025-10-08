package com.example.Porter.Utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

public class MapperUtils {

	public static <S, T> T map(S source, Class<T> targetClass) {
		try {
			T target = targetClass.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(source, target);
			return target;

		} catch (Exception e) {
			throw new RuntimeException("Mapping failed", e);
		}
	}

	public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
		return sourceList.stream().map(source -> map(source, targetClass)).collect(Collectors.toList());
	}

	public static <S, T> Page<T> mapPage(Page<S> sourcePage, Class<T> targetClass) {
		return sourcePage.map(source -> map(source, targetClass));
	}

}
