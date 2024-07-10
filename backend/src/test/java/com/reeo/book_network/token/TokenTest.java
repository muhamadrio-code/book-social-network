package com.reeo.book_network.token;

import com.reeo.book_network.ColumnSpec;
import com.reeo.book_network.user.User;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ContextConfiguration(classes = {Token.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenTest {

  @Test
  @DisplayName("Token should have no args constructor")
  public void testNoArgsConstructor() {
    Token myClassInstance = new Token();
    assertThat(myClassInstance).isNotNull();
  }

  @Test
  @DisplayName("Token should have all args constructor")
  public void testAllArgsConstructor() {
    Token myClassInstance = new Token(
        1, "",
        LocalDateTime.now(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        new User()
    );

    assertThat(myClassInstance).isNotNull();
  }

  @Test
  @DisplayName("Token should have builder class")
  public void testBuilderClass() {
    Token myClassInstance = Token.builder().build();
    assertThat(myClassInstance).isNotNull();
  }

  @ParameterizedTest(name = "Getter of field {0} has been invoked")
  @DisplayName("Should invoke Getter method")
  @MethodSource("getFieldsAndTypes")
  public void testGetter(String fieldName, String genericType) throws NoSuchFieldException, NoSuchMethodException {
    Token u = new Token();
    String declaredField = Token.class.getDeclaredField(fieldName).getName();
    String methodPrefix = genericType.equals("boolean") ? "is" : "get";
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = methodPrefix + methodSuffix;
    Method method = Token.class.getDeclaredMethod(methodName);
    assertDoesNotThrow(() -> method.invoke(u));
  }

  @ParameterizedTest(name = "Property {0} cannot have Setter method")
  @DisplayName("Token cannot have Setter method")
  @MethodSource("getFieldsAndTypes")
  public void testSetter(String fieldName) throws NoSuchFieldException {
    String declaredField = Token.class.getDeclaredField(fieldName).getName();
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = "set" + methodSuffix;
    assertThat(Arrays.stream(Token.class.getDeclaredMethods()).map(Method::getName))
        .doesNotContain(methodName);
  }

  @Test
  @DisplayName("Token id should annotated with GeneratedValue")
  void testTokenId() throws NoSuchFieldException {
    assertThat(Token.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class)).isTrue();
  }

  @DisplayName("Property of ")
  @ParameterizedTest(name = "{0} should have type of {1}")
  @MethodSource("getFieldsAndTypes")
  void userFieldsTest(String fieldName, String type) throws NoSuchFieldException {
    assertDoesNotThrow(() -> Token.class.getDeclaredField(fieldName));
    assertThat(Token.class.getDeclaredField(fieldName).getGenericType().getTypeName()).isEqualTo(type);
  }

  @Test
  @DisplayName("Table name must be token")
  void shouldCalledTokenTable() {
    Table annotation = Token.class.getAnnotation(Table.class);
    assertThat(annotation.name()).isEqualTo("token");
  }

  @Test
  @DisplayName("Token should have constraints ManyToOne to user")
  void shouldConstraints() throws NoSuchFieldException {
    assertThat(Token.class.getDeclaredField("user").isAnnotationPresent(ManyToOne.class))
        .isTrue();
  }

  @Test
  @DisplayName("Token user field should JoinColumn userId")
  void shouldJoinColumn() throws NoSuchFieldException {
    Field declaredField = Token.class.getDeclaredField("user");
    assertThat(declaredField.isAnnotationPresent(JoinColumn.class))
        .isTrue();
    assertThat(declaredField.getAnnotation(JoinColumn.class).name()).isEqualTo("userId");
  }

  @DisplayName("Token fields Annotation spec")
  @ParameterizedTest(name = "{0} should annotated with @Column")
  @MethodSource("getColumnFieldsName")
  void shouldAnnotatedWithColumn(String fieldName) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.isAnnotationPresent(Column.class)).isTrue();
  }

  @DisplayName("Token fields column name test")
  @ParameterizedTest(name = "{0} should have column name of {1}")
  @MethodSource("getFieldsNameAndColumnName")
  void testColumnSpecName(String fieldName, String columnName) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).name())
        .isEqualTo(columnName);
  }

  @DisplayName("Token fields column Unique test")
  @ParameterizedTest(name = "{0} should be Unique = {1} ")
  @MethodSource("getFieldsNameAndUniqueSpec")
  void testColumnUniqueSpec(String fieldName, boolean isUnique) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).unique())
        .isEqualTo(isUnique);
  }

  @DisplayName("Token fields column Nullable test")
  @ParameterizedTest(name = "{0} should be Nullable = {1} ")
  @MethodSource("getFieldsNameAndNullableSpec")
  void testColumnNullableSpec(String fieldName, boolean isNullable) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).nullable())
        .isEqualTo(isNullable);
  }

  @DisplayName("Token fields column Insertable test")
  @ParameterizedTest(name = "{0} should be Insertable = {1} ")
  @MethodSource("getFieldsNameAndInsertableSpec")
  void testColumnInsertableSpec(String fieldName, boolean isInsertable) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).insertable())
        .isEqualTo(isInsertable);
  }

  @DisplayName("Token fields column Updatable test")
  @ParameterizedTest(name = "{0} should be Updatable = {1} ")
  @MethodSource("getFieldsNameAndUpdatableSpec")
  void testColumnUpdatableSpec(String fieldName, boolean isUpdatable) throws NoSuchFieldException {
    Field field = Token.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).updatable())
        .isEqualTo(isUpdatable);
  }

  private List<String> getColumnFieldsName() {
    return List.of(
        "token",
        "createAt",
        "expiredAt",
        "verifiedAt"
    );
  }

  private Stream<Arguments> getFieldsNameAndColumnName() {
    List<String> columnFieldsName = getColumnFieldsName();
    return combineWithColumnSpec(columnFieldsName, ColumnSpec::name);
  }

  private Stream<Arguments> getFieldsNameAndUniqueSpec() {
    List<String> columnFieldsName = getColumnFieldsName();
    return combineWithColumnSpec(columnFieldsName, ColumnSpec::unique);
  }

  private Stream<Arguments> getFieldsNameAndNullableSpec() {
    List<String> columnFieldsName = getColumnFieldsName();
    return combineWithColumnSpec(columnFieldsName, ColumnSpec::nullable);
  }

  private Stream<Arguments> getFieldsNameAndInsertableSpec() {
    List<String> columnFieldsName = getColumnFieldsName();
    return combineWithColumnSpec(columnFieldsName, ColumnSpec::insertable);
  }

  private Stream<Arguments> getFieldsNameAndUpdatableSpec() {
    List<String> columnFieldsName = getColumnFieldsName();
    return combineWithColumnSpec(columnFieldsName, ColumnSpec::updateable);
  }

  private <T, E> Stream<Arguments> combineWithColumnSpec(List<T> l1, Function<ColumnSpec, E> function) {
    int sampleSize = l1.size();
    List<ColumnSpec> columnSpecs = getColumnSpecs();
    Stream.Builder<Arguments> builder = Stream.builder();
    for (int i = 0; i < sampleSize; i++) {
      Arguments arguments = Arguments.of(l1.get(i), function.apply(columnSpecs.get(i)));
      builder.add(arguments);
    }

    return builder.build();
  }

  private List<ColumnSpec> getColumnSpecs() {
    return List.of(
        new ColumnSpec("token", false, true, true, true),
        new ColumnSpec("create_at", false, false, true, true),
        new ColumnSpec("expired_at", false, false, true, true),
        new ColumnSpec("verified_at", true, false, true, true)
    );
  }

  private Stream<Arguments> getFieldsAndTypes() {
    return Stream.of(
        Arguments.of("id", "long"),
        Arguments.of("token", String.class.getName()),
        Arguments.of("createAt", LocalDateTime.class.getName()),
        Arguments.of("expiredAt", LocalDateTime.class.getName()),
        Arguments.of("verifiedAt", LocalDateTime.class.getName()),
        Arguments.of("user", User.class.getName())
    );
  }

}