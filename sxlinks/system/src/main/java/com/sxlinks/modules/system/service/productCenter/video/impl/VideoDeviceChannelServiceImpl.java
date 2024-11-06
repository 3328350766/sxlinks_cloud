package com.sxlinks.modules.system.service.productCenter.video.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.base.BaseTree;
import com.sxlinks.common.constant.ChannelIdType;
import com.sxlinks.common.constant.TreeType;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDevice;
import com.sxlinks.modules.system.entity.productCenter.video.VideoDeviceChannel;
import com.sxlinks.modules.system.mapper.productCenter.video.VideoDeviceChannelMapper;
import com.sxlinks.modules.system.mapper.productCenter.video.VideoDeviceMapper;
import com.sxlinks.modules.system.service.productCenter.video.IVideoDeviceChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @Author baba
 * @since 2022-06-02
 */
@Service
public class VideoDeviceChannelServiceImpl extends ServiceImpl<VideoDeviceChannelMapper, VideoDeviceChannel> implements IVideoDeviceChannelService {

    @Resource
    private VideoDeviceMapper videoDeviceMapper;

    @Resource
    private VideoDeviceChannelMapper videoDeviceChannelMapper;

    @Override
    public Integer add(VideoDeviceChannel deviceChannel) {
        return videoDeviceChannelMapper.add(deviceChannel);
    }

    @Override
    public Integer update(VideoDeviceChannel channel) {
        return baseMapper.update(channel);
    }

    @Override
    public IPage<VideoDeviceChannel> custom(IPage<VideoDeviceChannel> iPage, String deviceId) {
        return baseMapper.getChannelsByCivilCodePage(iPage,deviceId);
    }

    @Override
    public List<BaseTree<VideoDeviceChannel>> queryVideoDeviceTree(String deviceId, String parentId, boolean onlyCatalog) {
        VideoDevice device = videoDeviceMapper.getDeviceByDeviceId(deviceId);
        if (device == null) {
            return null;
        }
        if (parentId == null || parentId.equals(deviceId)) {
            // 字根节点开始查询
            List<VideoDeviceChannel> rootNodes = getRootNodes(deviceId, TreeType.CIVIL_CODE.equals(device.getTreeType()), true, !onlyCatalog);
            return transportChannelsToTree(rootNodes, "");
        }

        if (TreeType.CIVIL_CODE.equals(device.getTreeType())) {
            if (parentId.length()%2 != 0) {
                return null;
            }
            // 使用行政区划展示树
//            if (parentId.length() > 10) {
//                // TODO 可能是行政区划与业务分组混杂的情形
//                return null;
//            }

            if (parentId.length() == 10 ) {
                if (onlyCatalog) {
                    return null;
                }
                // parentId为行业编码， 其下不会再有行政区划
                List<VideoDeviceChannel> channels = baseMapper.getChannelsByCivilCode(deviceId, parentId);
                List<BaseTree<VideoDeviceChannel>> trees = transportChannelsToTree(channels, parentId);
                return trees;
            }
            // 查询其下的行政区划和摄像机
            List<VideoDeviceChannel> channelsForCivilCode = baseMapper.getChannelsWithCivilCodeAndLength(deviceId, parentId, parentId.length() + 2);
            if (!onlyCatalog) {
                List<VideoDeviceChannel> channels = baseMapper.getChannelsByCivilCode(deviceId, parentId);

                for(VideoDeviceChannel channel : channels) {
                    boolean flag = false;
                    for(VideoDeviceChannel deviceChannel : channelsForCivilCode) {
                        if(channel.getChannelId().equals(deviceChannel.getChannelId())) {
                            flag = true;
                        }
                    }
                    if(!flag) {
                        channelsForCivilCode.add(channel);
                    }
                }
            }
            List<BaseTree<VideoDeviceChannel>> trees = transportChannelsToTree(channelsForCivilCode, parentId);
            return trees;

        }
        // 使用业务分组展示树
        if (TreeType.BUSINESS_GROUP.equals(device.getTreeType())) {
            if (parentId.length() < 14 ) {
                return null;
            }
            List<VideoDeviceChannel> deviceChannels = baseMapper.queryChannels(deviceId, parentId, null, null, null);
            List<BaseTree<VideoDeviceChannel>> trees = transportChannelsToTree(deviceChannels, parentId);
            return trees;
        }
        return null;
    }

    private List<VideoDeviceChannel> getRootNodes(String deviceId, boolean isCivilCode, boolean haveCatalog, boolean haveChannel) {
        if (!haveCatalog && !haveChannel) {
            return null;
        }
        List<VideoDeviceChannel> result = new ArrayList<>();
        if (isCivilCode) {
            // 使用行政区划
            Integer length= baseMapper.getChannelMinLength(deviceId);
            if (length == null) {
                return null;
            }
            if (length <= 10) {
                if (haveCatalog) {
                    List<VideoDeviceChannel> provinceNode = baseMapper.getChannelsWithCivilCodeAndLength(deviceId, null, length);
                    if (provinceNode != null && provinceNode.size() > 0) {
                        result.addAll(provinceNode);
                    }
                }

                if (haveChannel) {
                    // 查询那些civilCode不在通道中的不规范通道，放置在根目录
                    List<VideoDeviceChannel> nonstandardNode = baseMapper.getChannelWithoutCiviCode(deviceId);
                    if (nonstandardNode != null && nonstandardNode.size() > 0) {
                        result.addAll(nonstandardNode);
                    }
                }
            }else {
                if (haveChannel) {
                    List<VideoDeviceChannel> deviceChannels = baseMapper.queryChannels(deviceId, null, null, null, null);
                    if (deviceChannels != null && deviceChannels.size() > 0) {
                        result.addAll(deviceChannels);
                    }
                }
            }

        }else {
            // 使用业务分组+虚拟组织

            // 只获取业务分组
            List<VideoDeviceChannel> deviceChannels = baseMapper.getBusinessGroups(deviceId, ChannelIdType.BUSINESS_GROUP);
            if (deviceChannels != null && deviceChannels.size() > 0) {
                result.addAll(deviceChannels);
            }
        }
        return result;
    }

    private List<BaseTree<VideoDeviceChannel>> transportChannelsToTree(List<VideoDeviceChannel> channels, String parentId) {
        if (channels == null) {
            return null;
        }
        List<BaseTree<VideoDeviceChannel>> treeNotes = new ArrayList<>();
        if (channels.size() == 0) {
            return treeNotes;
        }
        for (VideoDeviceChannel channel : channels) {

            BaseTree<VideoDeviceChannel> node = new BaseTree<>();
            node.setId(channel.getChannelId());
            node.setDeviceId(channel.getDeviceId());
            node.setName(channel.getName());
            node.setPid(parentId);
            node.setBasicData(channel);
            node.setParent(false);
            if (channel.getChannelId().length() > 8) {
                String gbCodeType = channel.getChannelId().substring(10, 13);
                node.setParent(gbCodeType.equals(ChannelIdType.BUSINESS_GROUP) || gbCodeType.equals(ChannelIdType.VIRTUAL_ORGANIZATION) );
            }else {
                node.setParent(true);
            }
            treeNotes.add(node);
        }
        Collections.sort(treeNotes);
        return treeNotes;
    }
}
