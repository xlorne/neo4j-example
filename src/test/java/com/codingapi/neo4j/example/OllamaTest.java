package com.codingapi.neo4j.example;

import com.codingapi.neo4j.example.llm.Ollama;
import org.junit.jupiter.api.Test;

public class OllamaTest {

    @Test
    void test(){
        Ollama ollama = new Ollama("qwen2.5:3b");
        String history = "华夏科技有限公司\n华夏科技发展有限公司\n";
        String completion = ollama.generate("""
                请生成一组单位名称数据，供大型管理系统使用。这些单位名称需要涵盖以下类别，体现多样性、真实性，并且避免重复和与实际机构完全一致：
                    1.	企业名称：例如“华夏科技有限公司”“金陵电子股份有限公司”。
                    2.	学校名称：例如“北京实验中学”“南京第一小学”。
                    3.	医院名称：例如“市立人民医院”“南方儿童医院”。
                    4.	政府部门：例如“市规划局”“省教育厅”。
                    5.	研究机构：例如“国家科技研究院”“华东生物研究所”。
                 要求：
                    1.	每个类别生成不少于50个名称。
                    2.	尽量加入地区信息（如“北京市”“江苏省”）。
                    3.	真实感强，但避免与真实机构完全重合或冒犯任何特定名称。
                    4.	格式化输出，如下所示：
                    企业：{名称}
                    学校：{名称}
                    医院：{名称}
                    政府部门：{名称}
                    研究机构：{名称}
                 输出示例：
                    华夏科技有限公司
                    南京实验中学
                    杭州市妇幼保健院
                    广州市税务局
                    中南大学材料研究中心
                 我当前已经创建的单位有:
                 {history}
                 帮我再创建一条单位名称，要满足系统的多样性测试，尽量避免数据太相似，您只需要返回一条数据，格式如下: 华夏科技有限公司，不添加任何其他信息
                """.replaceAll("\\{history}", history));
        System.out.println(completion);
    }
}
