# Sonar-PHP Control Flow Graph

## Definition

A CFG is a graph of [basic blocks](https://en.wikipedia.org/wiki/Basic_block) (from now on, we will use the term 'block'),
where block is sequence of statements executed linearly without branching.

### Blocks

#### Block attributes

* successors - set of blocks that are executed after specific block
* predecessors - set of blocks that are executed before specific block
* syntactic successor
  - an imaginary successor which exists only for blocks ending with unconditional jumps (BREAK, CONTINUE, RETURN, GOTO, THROW)
  - it is the "normal" successor in case the jump would be omitted
  - for non-jump blocks, it is `null`
* elements
  - list of trees (ASTs) inside the block which are executed sequentially
  - **Important** : the block does not necessarily end with a terminator
    - it could have a terminator if it contains a RETURN inside the list of trees
    - there are no artificial terminators

#### Block types

Different implementations of blocks:

* Simple block - one or multiple successors
* Branching block
  - has 2 successors - one TRUE successor and one FALSE successor
  - contains the branching tree - e.g. IfStatementTree or loop trees
* End block - no successors

### Statements

Statements that are considered for building blocks:

* TRY, THROW, RETURN, BREAK, CONTINUE, GOTO, LABEL
* DO_WHILE, WHILE, FOR, FOREACH, IF, SWITCH

Every other statement will be added as element to the containing block (including ternary expressions).

In other words, we do not explore the expressions inside the above statements when building the CFG, we just add them to the list of elements.

## Implementation

The CFG gets built in [ControlFlowGraphBuilder](php-frontend/src/main/java/org/sonar/php/cfg/ControlFlowGraphBuilder.java)

### For Loop
The FOR statement creates multiple blocks:

- one for initialization of variables (before body)
- one for the body of the for
- one for update (following the body)
- one for the condition

### Switch

The SWITCH statement is modelled as multiple if-elseif blocks.

### Try Catch Finally

The TRY statement is modelled as a block with multiple successors
- each CATCH block represents a successor
- the FINALLY block represents a successor

## Tests

The best documentation is Test Automation, see [ControlFlowGraphTest](php-frontend/src/test/java/org/sonar/php/cfg/ControlFlowGraphTest.java).
