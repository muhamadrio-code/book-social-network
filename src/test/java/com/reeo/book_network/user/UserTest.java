package com.reeo.book_network.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Faker;
import com.reeo.book_network.ColumnSpec;
import com.reeo.book_network.role.Role;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {User.class})
class UserTest {

  @Test
  @DisplayName("User should have no args constructor")
  public void testNoArgsConstructor() {
    User myClassInstance = new User();
    assertThat(myClassInstance).isNotNull();
  }

  @ParameterizedTest(name = "Getter of field {0} has been invoked")
  @DisplayName("Should invoke Getter method")
  @MethodSource("getFieldsAndTypes")
  public void testGetter(String fieldName, String genericType) throws NoSuchFieldException, NoSuchMethodException {
    User u = new User();
    String declaredField = User.class.getDeclaredField(fieldName).getName();
    String methodPrefix = genericType.equals("boolean") ? "is" : "get";
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = methodPrefix + methodSuffix;
    Method method = User.class.getDeclaredMethod(methodName);
    assertDoesNotThrow(() -> method.invoke(u));
  }

  @ParameterizedTest(name = "Property {0} cannot have Setter method")
  @DisplayName("User cannot have Setter method")
  @MethodSource("getFieldsAndTypes")
  public void testSetter(String fieldName) throws NoSuchFieldException {
    String declaredField = User.class.getDeclaredField(fieldName).getName();
    String methodSuffix = Character.toUpperCase(declaredField.charAt(0)) + declaredField.substring(1);
    String methodName = "set" + methodSuffix;
    assertThat(Arrays.stream(User.class.getDeclaredMethods()).map(Method::getName))
        .doesNotContain(methodName);
  }

  @Test
  @DisplayName("User get name should return the user email")
  public void testNameShouldReturnEmail() {
    User user = User.builder().email("example@mail.com").build();
    assertThat(user.getName()).isEqualTo("example@mail.com");
  }

  @Test
  @DisplayName("User get username should return the user email")
  public void testUsernameShouldReturnEmail() {
    User user = User.builder().email("example@mail.com").build();
    assertThat(user.getUsername()).isEqualTo("example@mail.com");
  }

  @Test
  @DisplayName("User password getter")
  void testGetPassword() {
    User user = User.builder().password("password").build();
    assertThat(user.getPassword()).isEqualTo("password");
  }

  @Test
  @DisplayName("User account should non locked")
  void testIsAccountNonLocked() {
    User user = User.builder().accountLocked(true).build();
    assertThat(user.isAccountNonLocked()).isFalse();
  }

  @Test
  @DisplayName("User account enabled")
  void testIsEnabled() {
    User user = User.builder().enabled(true).build();
    assertThat(user.isEnabled()).isTrue();
  }

  @Test
  @DisplayName("User id should annotated with GeneratedValue")
  void testUserId() throws NoSuchFieldException {
    assertThat(User.class.getDeclaredField("id").isAnnotationPresent(GeneratedValue.class)).isTrue();
  }

  @Test
  @DisplayName("User Authority should be instance of SimpleGrantedAuthority")
  public void testGetAuthorities() {
    Role role = Role.builder().name("USER").build();
    User user = User.builder().roles(List.of(role)).build();
    assertThat(user.getAuthorities())
        .allMatch(a -> a instanceof SimpleGrantedAuthority);
  }

  @DisplayName("User fullname must be ")
  @ParameterizedTest(name = "{2}")
  @MethodSource("generateUsers")
  void testGetFullname(String firstname, String lastname, String fullname) {
    User user = User.builder().firstName(firstname).lastName(lastname).build();
    assertThat(user.getFullname()).isEqualTo(fullname);
  }

  @DisplayName("Property of ")
  @ParameterizedTest(name = "{0} should have type of {1}")
  @MethodSource("getFieldsAndTypes")
  void userFieldsTest(String fieldName, String type) throws NoSuchFieldException {
    assertDoesNotThrow(() -> User.class.getDeclaredField(fieldName));
    assertThat(User.class.getDeclaredField(fieldName).getGenericType().getTypeName()).isEqualTo(type);
  }

  @DisplayName("User class Annotation spec")
  @ParameterizedTest(name = "must annotated with {0}")
  @ValueSource(classes = {Entity.class, EntityListeners.class, Table.class})
  void shouldAnnotatedWith(Class<? extends Annotation> annotationClass) {
    assertThat(User.class.isAnnotationPresent(annotationClass)).isTrue();
  }

  @Test
  @DisplayName("Table name must be _user")
  void shouldCalledUserTable() {
    Table annotation = User.class.getAnnotation(Table.class);
    assertThat(annotation.name()).isEqualTo("_user");
  }

  @Test
  @DisplayName("User should have constraints ManyToMany to roles")
  void shouldConstraintsManyToMany() throws NoSuchFieldException {
    assertThat(User.class.getDeclaredField("roles").isAnnotationPresent(ManyToMany.class))
        .isTrue();
  }

  @Test
  @DisplayName("User should ignore property roles on serialize/deserialize ")
  void shouldIgnoreRoles() throws NoSuchFieldException {
    assertThat(User.class.getDeclaredField("roles").isAnnotationPresent(JsonIgnore.class))
        .isTrue();
  }


  @DisplayName("User fields Annotation spec")
  @ParameterizedTest(name = "{0} should annotated with @Column")
  @MethodSource("getColumnFieldsName")
  void shouldAnnotatedWithColumn(String fieldName) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.isAnnotationPresent(Column.class)).isTrue();
  }

  @DisplayName("User fields column name test")
  @ParameterizedTest(name = "{0} should have column name of {1}")
  @MethodSource("getFieldsNameAndColumnName")
  void testColumnSpecName(String fieldName, String columnName) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).name())
        .isEqualTo(columnName);
  }

  @DisplayName("User fields column Unique test")
  @ParameterizedTest(name = "{0} should be Unique : {1} ")
  @MethodSource("getFieldsNameAndUniqueSpec")
  void testColumnUniqueSpec(String fieldName, boolean isUnique) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).unique())
        .isEqualTo(isUnique);
  }

  @DisplayName("User fields column Nullable test")
  @ParameterizedTest(name = "{0} should be Nullable : {1} ")
  @MethodSource("getFieldsNameAndNullableSpec")
  void testColumnNullableSpec(String fieldName, boolean isNullable) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).nullable())
        .isEqualTo(isNullable);
  }

  @DisplayName("User fields column Insertable test")
  @ParameterizedTest(name = "{0} should be Insertable : {1} ")
  @MethodSource("getFieldsNameAndInsertableSpec")
  void testColumnInsertableSpec(String fieldName, boolean isInsertable) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).insertable())
        .isEqualTo(isInsertable);
  }

  @DisplayName("User fields column Updatable test")
  @ParameterizedTest(name = "{0} should be Updatable : {1} ")
  @MethodSource("getFieldsNameAndUpdatableSpec")
  void testColumnUpdatableSpec(String fieldName, boolean isUpdatable) throws NoSuchFieldException {
    Field field = User.class.getDeclaredField(fieldName);
    assertThat(field.getDeclaredAnnotation(Column.class).updatable())
        .isEqualTo(isUpdatable);
  }

  private List<String> getColumnFieldsName() {
    return List.of(
        "firstName",
        "lastName",
        "email",
        "password",
        "accountLocked",
        "enabled",
        "createdDate",
        "lastModifiedDate"
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
        new ColumnSpec(
            "first_name",
            false,
            false,
            true,
            true
        ),
        new ColumnSpec(
            "last_name",
            false,
            false,
            true,
            true
        ),
        new ColumnSpec(
            "email",
            false,
            true,
            true,
            true
        ),
        new ColumnSpec(
            "password",
            false,
            false,
            true,
            true
        ),
        new ColumnSpec(
            "account_locked",
            false,
            false,
            true,
            true
        ),
        new ColumnSpec(
            "enabled",
            false,
            false,
            true,
            true
        ),
        new ColumnSpec(
            "created_date",
            false,
            false,
            false,
            true
        ),
        new ColumnSpec(
            "last_modified_date",
            true,
            false,
            true,
            false
        )
    );
  }

  private Stream<Arguments> getFieldsAndTypes() {
    return Stream.of(
        Arguments.of("id", "long"),
        Arguments.of("firstName", String.class.getName()),
        Arguments.of("lastName", String.class.getName()),
        Arguments.of("email", String.class.getName()),
        Arguments.of("password", String.class.getName()),
        Arguments.of("accountLocked", "boolean"),
        Arguments.of("enabled", "boolean"),
        Arguments.of("roles", "java.util.List<" + Role.class.getName() + ">"),
        Arguments.of("createdDate", LocalDateTime.class.getName()),
        Arguments.of("lastModifiedDate", LocalDateTime.class.getName())
    );
  }

  private Stream<Arguments> generateUsers() {
    Stream.Builder<Arguments> builder = Stream.builder();
    for (int i = 0; i < 5; i++) {
      String s = Faker.instance().name().firstName();
      String s1 = Faker.instance().name().lastName();

      builder.add(Arguments.of(s, s1, s + " " + s1));
    }
    return builder.build();
  }
}