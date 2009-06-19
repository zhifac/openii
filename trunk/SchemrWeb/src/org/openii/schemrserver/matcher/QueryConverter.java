package org.openii.schemrserver.matcher;

import org.openii.schemrserver.search.QueryFragment;

public interface QueryConverter {

	TokenSet getTokenSet(QueryFragment q);

}
