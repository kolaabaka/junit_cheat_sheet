package ru.banturov.junit_cheat_sheet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayName("Math utils work")
@TestInstance(Lifecycle.PER_CLASS) //Задаёт жизненный цикл тестов JUnit, второй Lifecycle.PER_METHOD
class MathUtilsTest {
//Тесты можно вызывать через project -> run as -> maven test, но для этого нужно написать плагин в pom.xml 
	MathUtils mathUtils;
	TestInfo testInfo;
	TestReporter testReporter;
	
	@BeforeAll
	void init(TestInfo testInfo, TestReporter testReporter) {
		this.testInfo = testInfo; //Дополнительные классы для логирования доп инфы
		this.testReporter = testReporter; //Ещё большие возможности для логирования, время и т.д.
		mathUtils = new MathUtils();
		System.out.println("Running " + testInfo.getDisplayName() + " with tag " + testInfo.getTags()); // 
		//Если сделать инит перед каждым методом, то выдаст в консоль информацию о каждом тесте 
	}
	
	@AfterAll
	static void finaliz() { //Должен быть статическим, чтобы не использовать экземпляр класса
		System.out.println("Tests finished");
	}
	
	@Test
	@Tag("Math") //Теги помогают группировать тесты, в Run Configuration можно создать конфиг под выбранный @Tag
	@DisplayName("Multiply test assertAll")
	void testMultiply() {
		System.out.println("Running " + testInfo.getDisplayName() + " with tag " + testInfo.getTags());
		testReporter.publishEntry("Reporter report");
		assertAll( //Позволяет объеденить однотипные тесты
				() -> assertEquals(4, mathUtils.multiply(2, 2)),
				() -> assertEquals(0, mathUtils.multiply(0, 2)),
				() -> assertEquals(-4, mathUtils.multiply(2, -2))
				);
	}
	
	@Nested //Пишется над классом, внутри можно использовать методы с аннотацией beforeAll и подобные, полноценный класс внутри класса
	@Tag("Math")
	@DisplayName("Add test class")
	class addTest{
		
		@Test
		@DisplayName("Add negative")
		void testAddnegative() {
			assertEquals(-5, mathUtils.add(-3, -2), "Add negative method is working wrong");
		}
		
		@Test
		@DisplayName("Add positive") //Меняет название метода в отображении всех  методов
		void testAddPositive() {
			boolean serverIsUp = true; 
			int expected = 4;
			int action = mathUtils.add(2, 2);
			assumeTrue(serverIsUp); //Условие при котором запустится тетс, например, проверка подключен сервер или нет
			assertEquals(expected, action, () -> "Add positive method is working wrong, expected " + expected + "but recived" + mathUtils.add(2, 2));
			//Лямбда позволяет выполнить условие только если тест даст неправильный ответ, если конкатинировать строку без лямбды, она будет
			//создаваться каждый раз, что может есть много ресурсов, если требуется сделать много
		}
	}
	
	
	@Test
	@Tag("Throw")
	@EnabledOnOs(OS.WINDOWS) //Целое семейство аннотаций, как пример, тест запустится только на Windows, есть другие
	void testDivide() {
		assertThrows(ArithmeticException.class, () -> mathUtils.divide(2, 0), "Divide by zero should throw exception"); //через лямбду
	}
	
	@Tag("Math")
	@RepeatedTest(4) //Используется вместо @Test и повторяет тест столько раз, скольку указано в аргументах
	void testcomputeCircleArea(RepetitionInfo repetitionInfo) {
		repetitionInfo.getFailureCount(); // Много методов, обект содержит итерацию теста, количество фейлов, колчество удачных попыток и т.д., можно использовать так как получится придумать, например написать условие виполнения разных тестов в зависимости от итерации
		assertEquals(314.159, mathUtils.computeCircleArea(10), 1, "Error, wrong circle area");
	}
	
	@Test
	@DisplayName("Should be disabled")
	@Disabled //Выключает тест
	void testDisabled() {
		fail("This method should be disabled");
	}
	
}
