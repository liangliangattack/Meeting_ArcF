package com.example.administrator.access_school_client.UI;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.access_school_client.R;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/8/30 21:44.
 */
public class Fragmentnews extends Fragment{

    private ListView listView;
    ImageView back;
    private String[] date = new String[]{"2018-2-9 16:16","2017-11-22 15:12","2017-11-10 15:10","2017-10-6 07:49","2017-7-15 13:55","2017-2-24 13:52","2016-10-9 09:21"};
    private String[] title1 = new String[]{"不忘初心，砥砺前行|2017年度总结表彰大会暨迎春联欢酒会报道",
            "【学无止境】2017年度售后培训会圆满落下帷幕",
            "【中标喜讯】公司再次成功中标墨西哥金属冶炼公司特种高温空调项目",
            "2017年销售座谈会专题报道",
            "省级小城镇考核有关专家莅临我公司考察",
            "公司三项创新成果获实用新型专利",
            "我司顺利通过2016年度三体系认证监督现场审核"};
    private String[] content1 = new String[]{"律回春晖渐，万象始更新。2月8日宁波惠康实业以“不忘初心，砥砺前行，幸福都是奋斗出来的”为主题的2017年度总结表彰大会暨迎春联欢酒会在泗门宾馆隆重举行，惠康集团董事长、监事长、惠康实业陈越增董事长等领导与全体员工欢聚一堂，共度新春佳节。2017年表现突出的优秀团队和先进个人进行了表彰，颁发了年度优秀经理人、先进部门、先进班组、先进工作者,迎春联欢酒会在欢乐喜庆的舞蹈中拉开序幕，随着幸运奖、三等奖、二等奖、一等奖抽取，不断掀起年会的高潮。迎春联欢酒会不仅给大家带来了欢声笑语，同时也让同事之间彼此的心更加贴近。公司领导与员工亲情互动、相互敬酒、激动人心的抽奖活动，让歌声、掌声、欢呼声一直在会场上荡漾，呈现出惠康这个大家庭的欢乐和谐。",
            //
            "11月7日公司组织召开2017年度售后培训会，各地办事处售后技术人员前来参加为期3天的售后培训会。由公司工程师讲解新产品售后服务技能，并对各类机组进行了逐一详细地技术交流。就售后服务中出现的“疑难杂症”、维修技巧、常见故障的解决方法等进行技术指导和探讨。\n" +
                    "\n" +
                    "随着惠康产品链不断丰富、保有量快速增长，提升售后服务技术能力，每年定期组织全国售后服务人员集中培训是其售后服务体系中的一个重要环节，希望能够借助此次培训，确实的提高售后服务中心专业技能，为客户提供更专业、更便捷的售后服务。",
            //
            "自2016年墨西哥著名金属冶炼公司订购一批“惠康”牌特种高温空调，在客户收到产品经过一年的试运行，对其产品质量及性能表示非常的满意。在2017年该项目的采购中，再次决定采购“惠康”牌特种高温空调，并将惠康列为该类产品的固定供应商。\n" +
                    "\n" +
                    "“惠康”牌特种高温空调引进德国特种空调技术，保证其产品的先进性，出口墨西哥的此款高性能特种高温空调采用了304不锈钢外壳及铜管铜翅片，对机组电器件进行了升级及优化，机组可以在环境温度10℃-75℃范围内正常运行，更具耐高温、抗震及防腐蚀特点。",
            //
            "9月21日-22日，公司2017年销售座谈会在慈溪杭州湾国际大酒店隆重举行，本次会议主题为总结上一冷冻年度工作，分析面临形势，做好下一年冷冻年度各项重点工作部署，夯实管理基础，明确公司发展战略。\n" +
                    "\n" +
                    "      议程一、由技术工程师对各办事处与会人员进行“空调报价软件”的专题培训。\n" +
                    "\n" +
                    "      议程二、公司生产、技术等部门，对各部门当前的工作存在的问题进行了深入分析，并对今后本部门的工作作出了新规划和安排，以如何服务市场一线需求为主要责任。\n" +
                    "\n" +
                    "      议程三、河南、河北办事处及宁波伯瑞斯作为办事处代表依次上台做了汇报，就当前宏观环境，同与会人员分享自己过去一年的工作经验与心得体会，并对2018冷冻年度做出美好展望。\n" +
                    "\n" +
                    "      议程四、公司董事长陈越增先生做重要讲话，回顾了公司2017冷冻年度的相关工作，既对公司个别部门及办事处工作中的不足进行了指正，也对公司各部门及办事处上一冷冻年度整体取得的成绩给予了充分的肯定，同时明确了公司近期的发展目标及规划，对下一冷冻年度的销售工作提出了更高的要求。\n" +
                    "\n" +
                    "      最后，由陈越增董事长主持公司主要部门负责人和办事处相关人员的畅谈会，以“企业流程改造、提升产品品质”为主题，座谈会上与会人员坦诚交流，为公司发展建言献策。\n" +
                    "\n" +
                    "　　此次会议的成功召开，公司销售队伍更加明确了新一冷冻年度的目标与工作重点，坚定了信心。我们相信以踏实奋进的态度投入工作，定会更加出色，捷报频传。",
            //
            "2016年3月11日，省级小城镇考核有关专家一行来泗门镇考察特色小城镇建设工作，我司作为特色小城镇项目考察其中一个站点，专家组在泗门镇党委书记、人事部总监王柏森及生产部总监陈平陪同下一同考察我司。\n" +
                    "\n" +
                    "考核专家小组实地查看了我公司厂区建设，对我公司环境及建设表示了充分的肯定和支持。",
            //
            "近期，公司技术人员发明的“一种低温送风装置”、“一种气液分离分配器”、“恒温制冷系统”三项实用技术获得中华人民共和国国家知识产权局颁发的实用新型专利证书，此三项创新成果均已成功应用于公司生产的部分工业特种空调及高精度空调等产品领域。不仅使公司产品更好地满足特殊工业领域工艺性空调制冷的需求，提升了产品运行安全可靠性，也显示了惠康实业雄厚的技术实力和对知识产权的自我保护意识。至此，公司已拥有国家专利技术84项，涵盖发明专利、实用新型专利、外观设计专利范畴。\n" +
                    "\n" +
                    "“一种低温送风装置”（证书号第5241061号），该技术发明通过在风道内设置除湿表冷器和深冷表冷器在气流接触深冷表冷器之前将气流中的水分除去，避免深冷表冷器结霜，确保整个低温送风装置能够保质保量德连续工作。\n" +
                    "\n" +
                    "“一种气液分离分配器”（证书号第5249133号），该技术发明通过气液分离装置与气液分配装置一体化，使得气液分离分配器紧凑而易于使用，且提高气液分配的均匀性，确保压缩机的工作稳定和安全。\n" +
                    "\n" +
                    "“恒温制冷系统”（证书号第5246518号），该技术发明通过压缩机旁路管道和冷却介质旁路管道确保压缩机工作平稳，也确保恒温制冷系统输出的制冷量稳定。",
            //
            "9月13日、14日，我司顺利开展2016年度三体系监督现场审核。CQC宁波评审中心专家组对公司的质量管理体系和安全、环境管理体系以及职业健康与安全管理体系进行了严格的监督审核工作。此次审核主要确定质量/环境/职业健康安全体系是否符合策划的安排，是否符合ISO9001/ISO14001/OHSAS18001 标准要求，是否符合公司管理体系要求，是否得到恰当有效的实施、保持以及持续改善。\n" +
                    "\n" +
                    "本次审核主要针对公司中央空调产品生产装配相关质量/环境/职业健康安全活动来开展。审核专家组通过召开抽查资料、询问相关人员、现场检查，全方位、多角度对公司的三体系认证管理进行了现场审核评价，认为公司质量、环境、职业健康安全管理模式不断完善，管理方式持续改进，监控检查力度更加有力，各部门工作更加规范，顺利通过“三体系” 认证监督现场审核。\n" +
                    "\n" +
                    "    此次体系外审工作的顺利通过，不仅验证了公司体系的有效性，也进一步强化了全体员工的质量意识，规范了工作行为，提升了公司整体管理素质和管理水平，公司在今后会认真做好质量管理体系文件的升级工作，不断推动公司质量管理体系的持续改进工作。\n"};
    private int[] picture = new int[]{R.drawable.news1,R.drawable.news2,R.drawable.news3,R.drawable.news4,R.drawable.news5,R.drawable.news6,R.drawable.news7};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_news,null);
        back  = rootview.findViewById(R.id.back_fn);
        back.setOnClickListener((View v)->getActivity().onBackPressed());
        listView = rootview.findViewById(R.id.listview_news);
        MyLVAdapter myLVAdapter = new MyLVAdapter();
        listView.setAdapter(myLVAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            //点击详情页面的跳转.....
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            FragmentNewsDetails fragmentNewsDetails = new FragmentNewsDetails();

            Bundle bundle = new Bundle();
            bundle.putInt("item",i);
            fragmentNewsDetails.setArguments(bundle);
            transaction.add(R.id.drawlayout_content,fragmentNewsDetails);
            transaction.addToBackStack(null).commit();
        });

        return rootview;
    }

    class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rootview = inflater.inflate(R.layout.simplenewsitem , null);
            TextView title = rootview.findViewById(R.id.simple_title);
            TextView content = rootview.findViewById(R.id.simple_content);
            TextView time = rootview.findViewById(R.id.simple_time);
            ImageView imageView = rootview.findViewById(R.id.imageview00);
            Log.e("________","&*********"+i);
            title.setText(title1[i]);
            content.setText(content1[i]);
            time.setText(date[i]);
            imageView.setImageResource(picture[i]);
//            imageView.setImageResource(R.drawable.news1);


            return rootview;
        }
    }
}
