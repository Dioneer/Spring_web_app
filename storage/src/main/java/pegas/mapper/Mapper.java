package pegas.mapper;

public interface Mapper<F,T> {
    T map(F from);

    default T map(F from, T t){
        return t;
    }
}
