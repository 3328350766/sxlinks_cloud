package com.sxlinks.service.system;


import com.sxlinks.bean.enumeration.BizExceptionEnum;
import com.sxlinks.bean.exception.ZhuqueException;
import com.sxlinks.utils.Lists;
import com.sxlinks.utils.ToolUtil;
import com.sxlinks.bean.entity.system.Menu;
import com.sxlinks.bean.vo.node.MenuNode;
import com.sxlinks.bean.vo.node.Node;
import com.sxlinks.bean.vo.node.ZTreeNode;
import com.sxlinks.dao.system.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created  on 2018/3/23 0023.
 *
 * @author gaoliang
 */
@Service
public class MenuService {

    private Logger logger = LoggerFactory.getLogger(MenuService.class);
    @Autowired
    private MenuRepository menuRepository;


    public void delMenu(Long menuId) {
        //删除菜单
        menuRepository.deleteById(menuId);
        //删除关联的relation
        menuRepository.deleteRelationByMenu(menuId);

    }

    public void delMenuContainSubMenus(Long menuId) {
        Menu menu = get(menuId);
        //删除所有子菜单
        List<Menu> menus = menuRepository.findByPcodesLike("%[" + menu.getCode() + "]%");
        menuRepository.deleteAll(menus);
        //删除当前菜单
        delMenu(menuId);

    }
    public List<MenuNode> getMenusByRoleIds(List<Long> roleList) {
        List menus = menuRepository.getMenusByRoleIds(roleList);
        return transferMenuNode(menus);

    }

    public List<MenuNode> getMenusTreeByRoleIds(List<Long> roleList) {
        List menus = menuRepository.getMenusByRoleIds(roleList);
        List<MenuNode> result =  generateTree(transferMenuNode(menus));
        for(MenuNode menuNode:result){
            if(!menuNode.getChildren().isEmpty()){
                sortTree(menuNode.getChildren());
                for(MenuNode menuNode1: menuNode.getChildren()){
                    if(!menuNode1.getChildren().isEmpty()) {
                        sortTree(menuNode1.getChildren());
                    }
                }
            }
        }
        sortTree(result);
        return result;
    }

    public List<MenuNode> getMenus() {
        List<MenuNode> list =  transferMenuNode(menuRepository.getMenus());
        List<MenuNode> result =  generateTree(list);


        for(MenuNode menuNode:result){
            if(!menuNode.getChildren().isEmpty()){
                sortTree(menuNode.getChildren());
                for(MenuNode menuNode1: menuNode.getChildren()){
                    if(!menuNode1.getChildren().isEmpty()) {
                        sortTree(menuNode1.getChildren());
                    }
                }
            }
        }
        sortTree(result);
        return result;
    }
    private void sortTree(List<MenuNode> list){
        Collections.sort(list, new Comparator<MenuNode>() {
            @Override
            public int compare(MenuNode o1, MenuNode o2) {
                return o1.getNum()-o2.getNum();
            }
        });
    }
    private List<MenuNode> generateTree(List<MenuNode> list){
        List<MenuNode> result = new ArrayList<>(20);
        Map<Long,MenuNode> map = Lists.toMap(list,"id");
        for(Map.Entry<Long,MenuNode> entry:map.entrySet()){
            MenuNode menuNode = entry.getValue();

            if(menuNode.getParentId().intValue()!=0){
                MenuNode parentNode = map.get(menuNode.getParentId());
                parentNode.getChildren().add(menuNode);
            }else{
                result.add(menuNode);
            }
        }
        return result;

    }
    private List<MenuNode> transferMenuNode(List menus){
        List<MenuNode> menuNodes = new ArrayList<>();
        try {
            for(int i=0;i<menus.size();i++){
                Object[] source = (Object[]) menus.get(i);
                MenuNode menuNode = new MenuNode();
                menuNode.setId(Long.valueOf(source[0].toString()));
                menuNode.setIcon(String.valueOf(source[1]));
                menuNode.setParentId(Long.valueOf(source[2].toString()));
                menuNode.setName(String.valueOf(source[3]));
                menuNode.setUrl(String.valueOf(source[4]));
                menuNode.setLevels((Integer) source[5]);
                menuNode.setIsmenu((Integer) source[6]);
                menuNode.setNum((Integer) source[7]);
                menuNode.setCode(String.valueOf(source[8]));
                menuNode.setStatus((Integer)source[9]);
                menuNodes.add(menuNode);

            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return menuNodes;
    }
    public List<ZTreeNode> menuTreeList() {
        List list = menuRepository.menuTreeList();
        List<ZTreeNode> nodes  =new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] source = (Object[]) list.get(i);
            ZTreeNode node = new ZTreeNode();
            node.setId(Long.valueOf(source[0].toString()));
            node.setpId(Long.valueOf(source[1].toString()));
            node.setName(source[2].toString());
            node.setIsOpen(Boolean.valueOf(source[3].toString()));
            nodes.add(node);
        }
        return nodes;
    }

    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        List list = menuRepository.menuTreeListByMenuIds(menuIds);
        List<ZTreeNode> nodes  =new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Object[] source = (Object[]) list.get(i);
            ZTreeNode node = new ZTreeNode();
            node.setId(Long.valueOf(source[0].toString()));
            node.setpId(Long.valueOf(source[1].toString()));
            node.setName(source[2].toString());
            node.setIsOpen(Boolean.valueOf(source[3].toString()));
            node.setChecked(Boolean.valueOf(source[4].toString()));
            nodes.add(node);
        }
        return nodes;
    }

    public void menuSetPcode(Menu menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {

            Menu pMenu = menuRepository.findByCode(menu.getPcode());
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new ZhuqueException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }

    public List<Node> generateMenuTreeForRole(List<ZTreeNode> list){

        List<Node> nodes = new ArrayList<>(20);
        for(ZTreeNode menu:list){
            Node permissionNode = new Node();
            permissionNode.setId(menu.getId());
            permissionNode.setName(menu.getName());
            permissionNode.setPid(menu.getpId());
            permissionNode.setChecked(menu.getChecked());
            nodes.add(permissionNode);
        }
        for(Node permissionNode:nodes){
            for(Node child:nodes){
                if(child.getPid().intValue() == permissionNode.getId().intValue()){
                    permissionNode.getChildren().add(child);
                }
            }
        }
        List<Node> result = new ArrayList<>(20);
        for(Node node:nodes){
            if(node.getPid().intValue() == 0){
                result.add(node);
            }
        }
        return result;


    }

    public Menu get(Long id) {
        Optional<Menu> optiona = menuRepository.findById(id);
        if (optiona.isPresent()) {
            return optiona.get();
        }
        return null;
    }
}
