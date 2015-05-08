package logic.selection;

import java.util.Collection;

public interface Selection<T> {

  Collection<T> selectFrom(Collection<T> from);

}
