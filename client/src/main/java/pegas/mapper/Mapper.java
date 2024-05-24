package pegas.mapper;

public interface Mapper<F,T>{
    T map(F f);
    default T map(F f, T t){return t;}
}
