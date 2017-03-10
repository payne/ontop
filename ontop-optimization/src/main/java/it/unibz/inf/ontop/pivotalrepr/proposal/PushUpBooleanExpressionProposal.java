package it.unibz.inf.ontop.pivotalrepr.proposal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import it.unibz.inf.ontop.model.ImmutableExpression;
import it.unibz.inf.ontop.pivotalrepr.CommutativeJoinOrFilterNode;
import it.unibz.inf.ontop.pivotalrepr.ExplicitVariableProjectionNode;
import it.unibz.inf.ontop.pivotalrepr.JoinOrFilterNode;
import it.unibz.inf.ontop.pivotalrepr.QueryNode;

import java.util.Optional;

public interface PushUpBooleanExpressionProposal extends QueryOptimizationProposal<ProposalResults> {


    /**
     * The expression to propagate up.
     * Note that it may not be the whole boolean expression attached to the provider node (but only some of its conjuncts)
     */
    ImmutableExpression getPropagatedExpression();

    /**
     * key: nodes providing the expression being pushed up
     * value (possibly empty): if only some conjuncts of the boolean expression attached to a node are propagated,
     * this is the conjunction of the remaining conjuncts
     */
    ImmutableMap<CommutativeJoinOrFilterNode, Optional<ImmutableExpression>> getProviderToNonPropagatedExpression();

    /**
     * Recipient of the expression.
     * If empty, a new filter node recipient will be created as the child of the blocking node
     */
    Optional<JoinOrFilterNode> getRecipientNode();

    //Node blocking further propagation
    QueryNode getUpMostPropagatingNode();

    //All nodes projecting variables on the path between provider and blocking node
    ImmutableList<ExplicitVariableProjectionNode> getInbetweenProjectors();
}
