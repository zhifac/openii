package org.openii.schemr.matcher;

import org.openii.schemr.client.model.QueryFragment;

public interface QueryConverter {

	TokenSet getTokenSet(QueryFragment q);

}
