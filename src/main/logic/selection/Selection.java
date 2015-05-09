package logic.selection;

import java.util.Set;

public interface Selection<T> {

  Set<T> selectFrom(Set<T> from);

}
