package comp2402a4;

import java.util.*;

public class GeometricTree extends BinaryTree<GeometricTreeNode> {

	public GeometricTree() {
		super (new GeometricTreeNode());
	}
	
	public void inorderDraw() {
		assignLevels();
                inOrderTraversal(r,-1);
	}
	
        protected int inOrderTraversal(GeometricTreeNode u,int i) {
	if( u == null){
                        return i;
            }else{
                i = inOrderTraversal(u.left,i);
                i=i+1;
                u.position.x = i;
                i=inOrderTraversal(u.right,i);
                return i;
            }
		
	}
	
	protected void randomX(GeometricTreeNode u, Random r) {
		if (u == null) return;
		u.position.x = r.nextInt(60);
		randomX(u.left, r);
		randomX(u.right, r);
	}
	
	//////////////////////////////////////leftistDraw start
	/**
	 * Draw each node so that it's x-coordinate is as small
	 * as possible without intersecting any other node at the same level 
	 * the same as its parent's
	 */
	public void leftistDraw() {
		assignLevels();
                LinkedList<GeometricTreeNode> list = new LinkedList();
                list.add(r);
                breadthFirstTraversal(list);
	}
        private void addChild(GeometricTreeNode u,LinkedList<GeometricTreeNode> list){
            if(u.left != null){
                list.addLast(u.left);
            }
            
            if(u.right != null){
                list.addLast(u.right);
            }
            
        }
        private void assignXPositions(LinkedList<GeometricTreeNode> list,int i){
            for(GeometricTreeNode u: list){
                u.position.x =i;
                i = i +1;
            }
            
        }
        protected void breadthFirstTraversal(LinkedList<GeometricTreeNode> list) {
		if( !list.isEmpty()){
                assignXPositions(list,0);
                LinkedList<GeometricTreeNode> nextLevelChildList = new LinkedList();
                for(GeometricTreeNode child: list){
                    addChild(child,nextLevelChildList);
                }
                breadthFirstTraversal(nextLevelChildList);
            }
		
	}
	//////////////////////////////////////leftistDraw ends
        
        
	///////////////////////////////////balanceDraw methods start
	public void balancedDraw() {
                HashMap<GeometricTreeNode, Boolean> map = getSubTreesCountMap();
                r.position.x = 0;
                r.position.y = 0;
                assignPosition(map,r,0);
	}
        protected void assignYPos(GeometricTreeNode u, int i) {
		if (u == null) return;
                u.position.y = i;
	}
        protected void assignPosition(HashMap<GeometricTreeNode, Boolean> map,GeometricTreeNode u, int x) {
            if (u == null) return;
            int verticalPos;
            if(u.parent != null){
                verticalPos = u.position.y;
            }else{
                verticalPos = 0;
            }
            u.position.x = x;
                if (map.get(u)){
                    assignYPos(u.left,verticalPos+1);
                    assignYPos(u.right,verticalPos);
                    assignPosition(map,u.left, x);
                    int height = height(u.left);
                    if(height == -1){
                        height = 0;
                    }
                    assignPosition(map, u.right, x+ height+1);
                }else{
                    assignYPos(u.left,verticalPos);
                    assignYPos(u.right,verticalPos+1);
                    int height = height(u.right);
                    if(height == -1){
                        height = 0;
                    }
                    assignPosition(map,u.left, x + height+1);
                    assignPosition(map, u.right, x);
                }
        }
	private HashMap<GeometricTreeNode, Boolean> getSubTreesCountMap(){
            HashMap<GeometricTreeNode, Boolean> map = new HashMap();
            postOrderTraversal(map,r);
            return map;
        }
        private int postOrderTraversal(HashMap<GeometricTreeNode, Boolean> map,GeometricTreeNode node){
            if( node == null){
                return 0;
            }else{
                int leftChildren = postOrderTraversal(map,node.left);
                int rightChildren = postOrderTraversal(map,node.right);
                if(leftChildren < rightChildren){
                    map.put(node, true);
                }else{
                    map.put(node, false);
                }
                return 1+leftChildren + rightChildren;
            }
        }
        ///////////////////////////////////balanceDraw methods end
	protected void assignLevels() {
		assignLevels(r, 0);
	}
	
	protected void assignLevels(GeometricTreeNode u, int i) {
		if (u == null) return;
		u.position.y = i;
		assignLevels(u.left, i+1);
		assignLevels(u.right, i+1);
	}
	
	public static void main(String[] args) {
		GeometricTree t = new GeometricTree();
		galtonWatsonTree(t, 100);
		System.out.println(t);
		t.inorderDraw();
		System.out.println(t);
	}
	
}
