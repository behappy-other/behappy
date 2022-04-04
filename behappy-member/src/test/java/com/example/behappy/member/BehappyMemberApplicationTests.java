//package com.example.behappy.member;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.io.file.FileReader;
//import cn.hutool.json.JSONUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.xiaowu.behappy.member.BehappyMemberApplication;
//import org.xiaowu.behappy.member.entity.AreaBean;
//import org.xiaowu.behappy.member.entity.AreaEntity;
//import org.xiaowu.behappy.member.service.AreaService;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//@SpringBootTest(classes = BehappyMemberApplication.class)
//class BehappyMemberApplicationTests {
//
//    static int i = 10000;
//    @Autowired
//    private AreaService areaService;
//
//    /**
//     * 最后核对下,https://xiangyuecn.gitee.io/areacity-jsspider-statsgov: 3级 jsonObject转换完成，共3635条数据，119726字节
//     */
//    @Test
//    void contextLoads() {
//        String path = "D:\\Download\\area_format_user.json";
//        FileReader fileReader = new FileReader(path);
//        String s = fileReader.readString();
//        List<AreaBean> areaRootBeans = JSONUtil.toList(s, AreaBean.class);
//
//        List<AreaEntity> areaEntities = new LinkedList<>();
//        List<String> ids = new ArrayList<>();
//        // 0级 1个
//        for (AreaBean areaRootBean : areaRootBeans) {
//            if (ids.contains(areaRootBean.getId())) {
//                areaRootBean.setId(String.valueOf(++i));
//            }
//            ids.add(areaRootBean.getId());
//            AreaEntity areaEntity = BeanUtil.copyProperties(areaRootBean, AreaEntity.class);
//            areaEntities.add(areaEntity);
//            // 1级 多个
//            for (AreaBean areaBean1 : areaRootBean.getChilds()) {
//                if (ids.contains(areaBean1.getId())) {
//                    areaBean1.setId(String.valueOf(++i));
//                }
//                ids.add(areaRootBean.getId());
//                AreaEntity areaEntity1 = BeanUtil.copyProperties(areaBean1, AreaEntity.class);
//                areaEntities.add(areaEntity1);
//                // 2级 多个
//                for (AreaBean areaBean2 : areaBean1.getChilds()) {
//                    if (ids.contains(areaBean2.getId())) {
//                        areaBean2.setId(String.valueOf(++i));
//                    }
//                    ids.add(areaRootBean.getId());
//                    AreaEntity areaEntity2 = BeanUtil.copyProperties(areaBean2, AreaEntity.class);
//                    areaEntities.add(areaEntity2);
//                }
//            }
//        }
//        areaService.saveBatch(areaEntities);
//    }
//
//
//}
