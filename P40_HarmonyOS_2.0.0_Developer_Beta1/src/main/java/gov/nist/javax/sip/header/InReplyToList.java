package gov.nist.javax.sip.header;

public final class InReplyToList extends SIPHeaderList<InReplyTo> {
    private static final long serialVersionUID = -7993498496830999237L;

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject, java.lang.Object
    public Object clone() {
        InReplyToList retval = new InReplyToList();
        retval.clonehlist(this.hlist);
        return retval;
    }

    public InReplyToList() {
        super(InReplyTo.class, "In-Reply-To");
    }
}
