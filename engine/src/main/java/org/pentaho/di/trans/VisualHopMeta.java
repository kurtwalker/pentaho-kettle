package org.pentaho.di.trans;

import org.pentaho.di.base.BaseHopMeta;
import org.pentaho.di.base.BaseMeta;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.step.StepMeta;
import org.w3c.dom.Node;

import java.util.List;

public class VisualHopMeta  extends BaseHopMeta<BaseMeta> {
  public VisualHopMeta( BaseMeta from, BaseMeta to ) {
    this.from = from;
    this.to = to;
    enabled = true;
  }
  public VisualHopMeta( Node visualHopNode, List<StepMeta> steps  ) {
    this.from = steps.stream()
      .filter( sm -> sm.getName().equals( XMLHandler.getTagValue( visualHopNode, TransHopMeta.XML_FROM_TAG ) ) )
      .findFirst().orElseThrow( RuntimeException::new );
    this.to = steps.stream()
      .filter( sm -> sm.getName().equals( XMLHandler.getTagValue( visualHopNode, TransHopMeta.XML_TO_TAG ) ) )
      .findFirst().orElseThrow( RuntimeException::new );
    enabled = true;
  }
  @Override public String getXML() throws KettleException {
    StringBuilder retval = new StringBuilder( 200 );

    if ( this.from != null && this.to != null ) {
      retval.append( "    " ).append( XMLHandler.openTag( XML_TAG ) ).append( Const.CR );
      retval.append( "      " ).append( XMLHandler.addTagValue( TransHopMeta.XML_FROM_TAG, this.from.getName() ) );
      retval.append( "      " ).append( XMLHandler.addTagValue( TransHopMeta.XML_TO_TAG, this.to.getName() ) );
      retval.append( "    " ).append( XMLHandler.closeTag( XML_TAG ) ).append( Const.CR );
    }

    return retval.toString();
  }

  private StepMeta searchStep( List<StepMeta> steps, String name ) {
    for ( StepMeta stepMeta : steps ) {
      if ( stepMeta.getName().equalsIgnoreCase( name ) ) {
        return stepMeta;
      }
    }

    return null;
  }

  public BaseMeta getFromStep() {
    return this.from;
  }

  public BaseMeta getToStep() {
    return this.to;
  }
}
