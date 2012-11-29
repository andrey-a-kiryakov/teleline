package org.teleline.io;

import java.util.regex.Pattern;

public class Validator {
	
	/**
	 * Проверяет имя сети
	 */
	public boolean validateNetName(String s) { return Pattern.compile("^.{1,50}$").matcher(s).matches(); }
	/**
	 * Проверяет название кросса
	 */
	public boolean validateDFrameName(String s) { return Pattern.compile("^.{1,50}$").matcher(s).matches(); }
	/**
	 * Проверяет имя абонента
	 */
	public boolean validateSubscriberName(String s) { return  Pattern.compile("^.{1,50}$").matcher(s).matches(); }
	/**
	 * Проверяет название включения
	 */
	public boolean validatePathName(String s) { return  Pattern.compile("^.{1,50}$").matcher(s).matches(); }
	/**
	 * Проверяет данные о паре перехода
	 */
	public boolean validatePathTransit(String s) { return  Pattern.compile("^[0-9]-[0-9]{2}$").matcher(s).matches(); }
	/**
	 * Проверяет номер шкафа
	 */
	public boolean validateCabinetNumber(String s) { return Pattern.compile("^[А-Яа-я0-9ЁёЙй]{1,4}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет номер колодца
	 */
	public boolean validateManholeNumber(String s) { return Pattern.compile("^[А-Яа-я0-9ЁёЙй]{1,4}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет емкость кабельной канализации
	 */
	public boolean validateDuctCapacity(String s) { return Pattern.compile("^[1-9][0-9]{0,1}$").matcher(s).matches(); }
	/**
	 * Проверяет длину кабельной канализации
	 */
	public boolean validateDuctLenght(String s) { return Pattern.compile("^[1-9][0-9]{0,3}$").matcher(s).matches(); }
	/**
	 * Проверяет диаметр кабельного канала
	 */
	public boolean validateTubeDiametr(String s) { return Pattern.compile("^[1-9][0-9]{0,2}$").matcher(s).matches(); }
	
	/**
	 * Проверяет номер здания
	 */
	public boolean validateBuildingNumber(String s) { return Pattern.compile("^[А-Яа-я0-9ЁёЙй]{1,4}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет номер бокса
	 */
	public boolean validateBoxNumber(String s) { return Pattern.compile("^[1-9][0-9]{0,2}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет номер громполосы
	 */
	public boolean validateFrameNumber(String s) { return Pattern.compile("^[1-9][0-9]{0,1}|[0]$").matcher(s).matches(); }
	
//	public boolean validateDBoxNumber(String s) { return Pattern.compile("^[1-9][0-9]{0,1}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет номер телефона
	 */
	public boolean validatePhoneNumber(String s) { return Pattern.compile("^[0-9]{3,7}$").matcher(s).matches(); }
	/**
	 * Проверяет номер кабеля
	 */
	public boolean validateCableNumber(String s) { return Pattern.compile("^[1-9][0-9]{0,2}|[0]$").matcher(s).matches(); }
	/**
	 * Проверяет длину кабеля
	 */
	public boolean validateCableLenght(String s) { return Pattern.compile("^[1-9][0-9]{0,3}$").matcher(s).matches(); }
	/**
	 * Проверяет диаметр жилы кабеля
	 */
	public boolean validateCableWireDiametr(String s) { return Pattern.compile("^[0-4],[0-9]$").matcher(s).matches(); }
	/**
	 * Проверяет год прокладки кабеля 
	 */
	public boolean validateCableYear(String s) { return Pattern.compile("^[0-9]{4}$").matcher(s).matches(); }
	
	/**
	 * Проверяет класс шкафа
	 */
	public boolean validateCabinetClass(String s) { return Pattern.compile("^[1]|[2]$").matcher(s).matches(); }
	/**
	 * Проверяет дополнительные параметры большой длины
	 */
	public boolean validateLongParametr(String s) { return Pattern.compile("^[\\s\\S]{0,300}$").matcher(s).matches(); }
	/**
	 * Проверяет дополнительные параметры
	 */
	public boolean validateOtherParametr(String s) { return Pattern.compile("^.{0,150}$").matcher(s).matches(); }
	/**
	 * Проверяет дату
	 */
	public boolean validateDate(String s) { return Pattern.compile("^[0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}$|^$").matcher(s).matches(); }
	public boolean validateDateRequired(String s) { return Pattern.compile("^[0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}$").matcher(s).matches(); }
	
}