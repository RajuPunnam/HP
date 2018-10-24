package com.ts.permissions.servicelayer.dto.converter;

import java.util.List;

import com.ts.permissions.servicelayer.dto.populator.Populator;

public class Converter<SOURCE, TARGET> implements org.springframework.core.convert.converter.Converter<SOURCE, TARGET> {

	private List<Populator<SOURCE, TARGET>> populators;

	private Class<TARGET> targetClass;

	@SuppressWarnings("unchecked")
	public TARGET convert(SOURCE arg0) {
		TARGET target = null;
		try {
			target = targetClass == null ? createTarget() : createFromClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Populator poulator : populators) {
			poulator.populate(arg0, target);
		}
		return target;
	}

	public void setTargetClass(final Class<TARGET> targetClass) {
		this.targetClass = targetClass;

		// sanity check - can we instantiate that class ?
		if (targetClass != null) {
			createFromClass();
		}
	}

	protected TARGET createFromClass() {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected TARGET createTarget() throws Exception {
		// optional - no longer requiring sub classes to implement this method
		throw new Exception();
	}

	public Class<TARGET> getTargetClass() {
		return targetClass;
	}

	public List<Populator<SOURCE, TARGET>> getPopulators() {
		return populators;
	}

	public void setPopulators(List<Populator<SOURCE, TARGET>> populators) {
		this.populators = populators;
	}
}
