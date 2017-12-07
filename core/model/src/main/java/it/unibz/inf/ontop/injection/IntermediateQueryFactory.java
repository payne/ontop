package it.unibz.inf.ontop.injection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.iq.node.*;
import it.unibz.inf.ontop.iq.*;
import it.unibz.inf.ontop.iq.tools.ExecutorRegistry;
import it.unibz.inf.ontop.model.atom.AtomPredicate;
import it.unibz.inf.ontop.model.atom.DataAtom;
import it.unibz.inf.ontop.model.atom.DistinctVariableOnlyDataAtom;
import it.unibz.inf.ontop.model.atom.RelationPredicate;
import it.unibz.inf.ontop.model.term.ImmutableExpression;
import it.unibz.inf.ontop.model.term.ImmutableTerm;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.substitution.ImmutableSubstitution;

import java.util.Optional;

/**
 * Factory following the Guice AssistedInject pattern.
 *
 * See https://github.com/google/guice/wiki/AssistedInject.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface IntermediateQueryFactory {

    IntermediateQueryBuilder createIQBuilder(DBMetadata metadata, ExecutorRegistry executorRegistry);

    ConstructionNode createConstructionNode(ImmutableSet<Variable> projectedVariables);

    ConstructionNode createConstructionNode(ImmutableSet<Variable> projectedVariables,
                                            ImmutableSubstitution<ImmutableTerm> substitution);

    ConstructionNode createConstructionNode(ImmutableSet<Variable> projectedVariables,
                                            ImmutableSubstitution<ImmutableTerm> substitution,
                                            Optional<ImmutableQueryModifiers> queryModifiers);

    UnionNode createUnionNode(ImmutableSet<Variable> projectedVariables);

    InnerJoinNode createInnerJoinNode();
    InnerJoinNode createInnerJoinNode(ImmutableExpression joiningCondition);
    InnerJoinNode createInnerJoinNode(Optional<ImmutableExpression> joiningCondition);

    LeftJoinNode createLeftJoinNode();
    LeftJoinNode createLeftJoinNode(ImmutableExpression joiningCondition);
    LeftJoinNode createLeftJoinNode(Optional<ImmutableExpression> joiningCondition);

    FilterNode createFilterNode(ImmutableExpression joiningCondition);

    IntensionalDataNode createIntensionalDataNode(DataAtom<AtomPredicate> atom);
    ExtensionalDataNode createExtensionalDataNode(DataAtom<RelationPredicate> atom);

    EmptyNode createEmptyNode(ImmutableSet<Variable> projectedVariables);

    TrueNode createTrueNode();

    UnaryIQTree createUnaryIQTree(UnaryOperatorNode rootNode, IQTree child, boolean declareAsLifted);

    /**
     * Not declared as lifted
     */
    UnaryIQTree createUnaryIQTree(UnaryOperatorNode rootNode, IQTree child);

    BinaryNonCommutativeIQTree createBinaryNonCommutativeIQTree(BinaryNonCommutativeOperatorNode rootNode,
                                                                @Assisted("left") IQTree leftChild,
                                                                @Assisted("right") IQTree rightChild,
                                                                boolean declareAsLifted);
    /**
     * Not declared as lifted
     */
    BinaryNonCommutativeIQTree createBinaryNonCommutativeIQTree(BinaryNonCommutativeOperatorNode rootNode,
                                                                @Assisted("left") IQTree leftChild,
                                                                @Assisted("right") IQTree rightChild);

    NaryIQTree createNaryIQTree(NaryOperatorNode rootNode, ImmutableList<IQTree> children, boolean declareAsLifted);

    /**
     * Not declared as lifted
     */
    NaryIQTree createNaryIQTree(NaryOperatorNode rootNode, ImmutableList<IQTree> children);

    IQ createIQ(DistinctVariableOnlyDataAtom projectionAtom, IQTree tree);
}
