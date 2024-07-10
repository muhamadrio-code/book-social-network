package com.reeo.book_network.role;


import com.reeo.book_network.ColumnSpec;
import com.reeo.book_network.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

@ContextConfiguration(classes = {Role.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleTest {
  @Test
  @DisplayName("Role should have no args constructor")
  public void testNoArgsConstructor() {
    Role myClassInstance = new Role();
    assertThat(myClassInstance).isNotNull();
  }

  @Test
  @DisplayName("Role should have all args constructor")
  public void testAllArgsConstructor() {
    Role myClassInstance = new Role(
        1, "",
        List.of(new User()),
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    assertThat(myClassInstance).isNotNull();
  }

  @Test
  @DisplayName("Role should have builder class")
  public void testBuilderClass() {
    Role myClassInstance = Role.builder().build();
    assertThat(myClassInstance).isNotNull();
  }

  @ParameterizedTest(name = "Getter of field {0} has been invoked")
  @DisplayName("Should invoke Getter method")
  @MethodSource("getFieldsAndTypes")
  public void testGetter(String fieldName, String genericType) throws NoSuchFieldException, NoSuchMethodException {
    Role o = new Role();
    String declaredField = Role.class.getDeclaredField(fieldName).getName();
    String methodPrefix = genericType.equals("boolean") ? "is" : "get";
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = methodPrefix + methodSuffix;
    Method method = Role.class.getDeclaredMethod(methodName);
    assertDoesNotThrow(() -> method.invoke(o));
  }

  @ParameterizedTest(name = "Property {0} cannot have Setter method")
  @DisplayName("Role cannot have Setter method")
  @MethodSource("getFieldsAndTypes")
  public void testSetter(String fieldName) throws NoSuchFieldException {
    String declaredField = Role.class.getDeclaredField(fieldName).getName();
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = "set" + methodSuffix;
    assertThat(Arrays.stream(Role.class.getDeclaredMethods()).map(Method::getName))
        .doesNotContain(methodName);
  }

  @Test
  @DisplayName("Role id should annotated with @GeneratedValue")
  void testRoleId() throws NoSuchFieldException {
    assertThat(Role.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class)).isTrue();
  }

  @Test
  @DisplayName("Role createdDate should annotated with @CreatedDate")
  void testRoleCreatedDate() throws NoSuchFieldException {
    assertThat(Role.class.getDeclaredField("createdDate").isAnnotationPresent(CreatedDate.class)).isTrue();
  }

  @Test
  @DisplayName("Role lastModifiedDate should annotated with @LastModifiedDate")
  void testRoleLastModifiedDate() throws NoSuchFieldException {
    assertThat(Role.class.getDeclaredField("lastModifiedDate").isAnnotationPresent(LastModifiedDate.class)).isTrue();
  }

  @DisplayName("Property of ")
  @ParameterizedTest(name = "{0} should have type of {1}")
  @MethodSource("getFieldsAndTypes")
  void userFieldsTest(String fieldName, String type) throws NoSuchFieldException {
    assertDoesNotThrow(() -> Role.class.getDeclaredField(fieldName));
    assertThat(Role.class.getDeclaredField(fieldName).getGenericType().getTypeName()).isEqualTo(type);
  }

  @Test
  @DisplayName("Table name must be role")
  void shouldCalledRoleTable() {
    Table annotation = Role.class.getAnnotation(Table.class);
    assertThat(annotation.name()).isEqualTo("role");
  }

  @Test
  @DisplayName("Role should have constraints ManyToMany to user")
  void shouldConstraints() throws NoSuchFieldException {
    assertThat(Role.class.getDeclaredField("users").isAnnotationPresent(ManyToMany.class))
        .isTrue();
  }

  @DisplayName("Role fields Annotation spec")
  @ParameterizedTest(name = "{0} should annotated with @Column")
  @MethodSource("getColumnFieldsName")
  void shouldAnnotatedWithColumn(String fieldName) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.isAnnotationPresent(Column.class)).isTrue();
  }

  @DisplayName("Role fields column name test")
  @ParameterizedTest(name = "{0} should have column name of {1}")
  @MethodSource("getFieldsNameAndColumnName")
  void testColumnSpecName(String fieldName, String columnName) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).name())
        .isEqualTo(columnName);
  }

  @DisplayName("Role fields column Unique test")
  @ParameterizedTest(name = "{0} should be Unique = {1} ")
  @MethodSource("getFieldsNameAndUniqueSpec")
  void testColumnUniqueSpec(String fieldName, boolean isUnique) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).unique())
        .isEqualTo(isUnique);
  }

  @DisplayName("Role fields column Nullable test")
  @ParameterizedTest(name = "{0} should be Nullable = {1} ")
  @MethodSource("getFieldsNameAndNullableSpec")
  void testColumnNullableSpec(String fieldName, boolean isNullable) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).nullable())
        .isEqualTo(isNullable);
  }

  @DisplayName("Role fields column Insertable test")
  @ParameterizedTest(name = "{0} should be Insertable = {1} ")
  @MethodSource("getFieldsNameAndInsertableSpec")
  void testColumnInsertableSpec(String fieldName, boolean isInsertable) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).insertable())
        .isEqualTo(isInsertable);
  }

  @DisplayName("Role fields column Updatable test")
  @ParameterizedTest(name = "{0} should be Updatable = {1} ")
  @MethodSource("getFieldsNameAndUpdatableSpec")
  void testColumnUpdatableSpec(String fieldName, boolean isUpdatable) throws NoSuchFieldException {
    Field field = Role.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).updatable())
        .isEqualTo(isUpdatable);
  }

  private List<String> getColumnFieldsName() {
    return List.of(
        "name",
        "createdDate",
        "lastModifiedDate"
    );
  }

  private List<ColumnSpec> getColumnSpecs() {
    return List.of(
        new ColumnSpec("name", false, true, true, true),
        new ColumnSpec("created_date", false, false, false, true),
        new ColumnSpec("last_modified_date", true, false, true, false)
    );
  }

  private Stream<Arguments> getFieldsAndTypes() {
    return Stream.of(
        Arguments.of("id", "long"),
        Arguments.of("name", String.class.getName()),
        Arguments.of("users", "java.util.List<" + User.class.getName() + ">"),
        Arguments.of("createdDate", LocalDateTime.class.getName()),
        Arguments.of("lastModifiedDate", LocalDateTime.class.getName())
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
}