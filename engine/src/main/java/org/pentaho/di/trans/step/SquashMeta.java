package org.pentaho.di.trans.step;

import org.pentaho.di.base.BaseMeta;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import java.util.List;
import java.util.stream.Collectors;

public class SquashMeta implements BaseMeta {
  private final Point location;
  private final String name;
  private List<String> stepNames;

  public SquashMeta( Point location, String name, List<String> stepNames ) {
    this.location = location;
    this.name = name;
    this.stepNames = stepNames;
  }

  public SquashMeta( Node squashNode ) {
    name = XMLHandler.getTagValue( squashNode, "name" );
    location = new Point(
      Integer.parseInt( XMLHandler.getTagValue( squashNode, "x" ) ),
      Integer.parseInt( XMLHandler.getTagValue( squashNode, "y" ) ) );
    Node stepsNode = XMLHandler.getSubNode( squashNode, "steps" );
    this.stepNames =
      XMLHandler.getNodes( stepsNode, "name" ).stream().map( XMLHandler::getNodeValue ).collect( Collectors.toList() );
  }

  @Override public Point getLocation() {
    return location;
  }

  @Override public String getName() {
    return name;
  }

  public String getXML() throws KettleException {
    StringBuilder retval = new StringBuilder( 200 );
    retval.append( "  " ).append( XMLHandler.openTag( "squash" ) ).append( Const.CR );
    retval.append( "    " ).append( XMLHandler.addTagValue( "name", getName() ) );
    retval.append( "    " ).append( XMLHandler.addTagValue( "x", location.x ) );
    retval.append( "    " ).append( XMLHandler.addTagValue( "y", location.y ) );
    retval.append( "  " ).append( XMLHandler.openTag( "steps" ) ).append( Const.CR );
    for ( String stepName : stepNames ) {
      retval.append( "    " ).append( XMLHandler.addTagValue( "name", stepName ) );
    }
    retval.append( "  " ).append( XMLHandler.closeTag( "steps" ) ).append( Const.CR );
    retval.append( "  " ).append( XMLHandler.closeTag( "squash" ) ).append( Const.CR );
    return retval.toString();
  }
}
