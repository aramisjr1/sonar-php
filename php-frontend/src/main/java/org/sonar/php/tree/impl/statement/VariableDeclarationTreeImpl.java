/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010 SonarSource and Akram Ben Aissi
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.php.tree.impl.statement;

import com.google.common.collect.Iterators;
import org.sonar.php.tree.impl.PHPTree;
import org.sonar.php.tree.impl.lexical.InternalSyntaxToken;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.declaration.VariableDeclarationTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.IdentifierTree;
import org.sonar.plugins.php.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.php.api.visitors.TreeVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

public class VariableDeclarationTreeImpl extends PHPTree implements VariableDeclarationTree {

  private final static Kind KIND = Kind.VARIABLE_DECLARATION;

  private final IdentifierTree variableIdentifier;
  private final InternalSyntaxToken equalToken;
  private final ExpressionTree initialisation;

  public VariableDeclarationTreeImpl(IdentifierTree variableIdentifier, @Nullable InternalSyntaxToken equalToken, @Nullable ExpressionTree initialisation) {
    this.variableIdentifier = variableIdentifier;
    this.equalToken = equalToken;
    this.initialisation = initialisation;
  }

  @Override
  public Kind getKind() {
    return KIND;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(variableIdentifier, equalToken, initialisation);
  }

  @Override
  public IdentifierTree variableIdentifier() {
    return variableIdentifier;
  }

  @Nullable
  @Override
  public SyntaxToken equalToken() {
    return equalToken;
  }

  @Nullable
  @Override
  public ExpressionTree initialisation() {
    return initialisation;
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitVariableDeclaration(this);
  }
}