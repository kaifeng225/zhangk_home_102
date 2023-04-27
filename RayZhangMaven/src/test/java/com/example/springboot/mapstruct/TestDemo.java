package com.example.springboot.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.springboot.mapstruct.converter.demo.CarMapper;
import com.example.springboot.mapstruct.domain.Car;
import com.example.springboot.mapstruct.domain.CarDto;
import com.example.springboot.mapstruct.domain.CarType;

public class TestDemo {
	
	@Test
	public void shouldMapCarToDto() {
	    //given
	    Car car = new Car( "Morris", 5, CarType.SEDANCAR,"ignore" );
	 
	    //when
	    CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );
	 
	    //then
	    assertThat( carDto ).isNotNull();
	    assertThat( carDto.getMake() ).isEqualTo( "Morris" );
	    assertThat( carDto.getSeatCount() ).isEqualTo( 5 );
	    assertThat( carDto.getType() ).isEqualTo( "SEDANCAR" );
	    assertThat( carDto.getCreateBy() ).isEqualTo( "ignore" );
	}
	
	@Test
	public void shouldMapDtoToCar1() {
	    //given
		CarDto carDto = new CarDto( "Morris", 5, "SEDANCAR","ignore" );
	 
	    //when
	    Car car = CarMapper.INSTANCE.carDtoToCar( carDto );
	 
	    //then
	    assertThat( car ).isNotNull();
	    assertThat( car.getMake() ).isEqualTo( "Morris" );
	    assertThat( car.getNumberOfSeats() ).isEqualTo( 5 );
	    assertThat( car.getType() ).isEqualTo( CarType.SEDANCAR );
	    assertThat( car.getCreateBy() ).isNull();
	}
	
	@Test
	public void shouldMapDtoToCar2() {
	    //given
		CarDto carDto = new CarDto( "Morris", 5, "SEDANCAR","ignore" );
	 
	    //when
	    Car car = new Car();
	    CarMapper.INSTANCE.carDtoIntoCar(carDto, car);
	 
	    //then
	    assertThat( car ).isNotNull();
	    assertThat( car.getMake() ).isEqualTo( "Morris" );
	    assertThat( car.getNumberOfSeats() ).isEqualTo( 5 );
	    assertThat( car.getType() ).isEqualTo( CarType.SEDANCAR );
	    assertThat( car.getCreateBy() ).isNull();
	}
}
