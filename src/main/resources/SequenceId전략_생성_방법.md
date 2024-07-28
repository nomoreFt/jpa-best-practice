~~~java

@Id

/**
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo")
@GenericGenerator(name = "hilo",
type = SequenceStyleGenerator.class,
parameters = {
@org.hibernate.annotations.Parameter(name = "sequence_name", value = "hilo_seq"),
@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
@org.hibernate.annotations.Parameter(name = "increment_size", value = "100"),
@org.hibernate.annotations.Parameter(name = "optimizer", value = "hilo")
})
**/

@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
@jakarta.persistence.SequenceGenerator(
name = "author_seq",
sequenceName = "author_seq",
initialValue = 1,
allocationSize = 20
)


private Long id;

~~~

기본적으로 두 상황이 동일하게 적용된다.
