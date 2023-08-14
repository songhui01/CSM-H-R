package context.utility;

import java.util.Iterator;
import java.util.List;

import context.core.ContextAttributeSnapshot;
import context.core.ContextSituationState;

public class ListHelper {
	
	//String, and template
	public static boolean notInList(Object o, List list){
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object tmp = (Object)it.next();
			if(o.toString().equals(tmp.toString())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Only is used to compare the contextAttributeSnapshotList
	 * @param s1 it is the new one
	 * @param s2 it is the old one
	 * @return
	 */
	public static boolean cssEqual(ContextSituationState s1, ContextSituationState s2){
		boolean tag = true;
		Iterator it = s1.getContextAttributeSnapshotList().iterator();
		while(it.hasNext()){
			ContextAttributeSnapshot tmp = (ContextAttributeSnapshot)it.next();
			if(inTheSnapshotList(tmp, s2.getContextAttributeSnapshotList())){
				continue;
			}else{
				tag = false;
				break;
			}
		}
		return tag;
	}
	
	/**
	 * 
	 * @param s1
	 * @param snaplist
	 * @return
	 */
	public static boolean inTheSnapshotList(ContextAttributeSnapshot s1, List<ContextAttributeSnapshot> snaplist){
		boolean tag = false;
		Iterator it = snaplist.iterator();
		while(it.hasNext()){
			ContextAttributeSnapshot tmp = (ContextAttributeSnapshot)it.next();
			//System.out.print(tmp.getStateURI()+"   ");
			//System.out.println(s1.getStateURI());
			if(tmp.getStateURI()!=null&&tmp.getAttributeURI().equals(s1.getAttributeURI())
					&&tmp.getObjectURI().equals(s1.getObjectURI())
					&&tmp.getStateURI().equals(s1.getStateURI())){
				tag=true;
			}
		}
		return tag;
	}
	
	
}
