package org.openii.schemr.matcher;

import org.openii.schemr.QueryFragment;

public interface QueryConverter {

	TokenSet getTokenSet(QueryFragment q);

}
