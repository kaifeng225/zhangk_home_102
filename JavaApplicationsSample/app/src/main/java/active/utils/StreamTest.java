package active.utils;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class StreamTest {
	
	private void testCollectors() {
		StreamStudent s1 = new StreamStudent("aa", 10,1);
		StreamStudent s2 = new StreamStudent("bb", 20,2);
		StreamStudent s3 = new StreamStudent("cc", 10,3);
		List<StreamStudent> list = Arrays.asList(s1, s2, s3);
		 
		//装成list
		List<Integer> ageList = list.stream().map(StreamStudent::getAge).collect(Collectors.toList()); // [10, 20, 10]
		 
		//转成set
		Set<Integer> ageSet = list.stream().map(StreamStudent::getAge).collect(Collectors.toSet()); // [20, 10]
		 
		//转成map,注:key不能相同，否则报错
		Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(StreamStudent::getName, StreamStudent::getAge)); // {cc=10, bb=20, aa=10}
		 
		//字符串分隔符连接
		String joinName = list.stream().map(StreamStudent::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)
		 
		//聚合操作
		//1.学生总数
		Long count = list.stream().collect(Collectors.counting()); // 3
		//2.最大年龄 (最小的minBy同理)
		Integer maxAge = list.stream().map(StreamStudent::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
		//3.所有人的年龄
		Integer sumAge = list.stream().collect(Collectors.summingInt(StreamStudent::getAge)); // 40
		//4.平均年龄
		Double averageAge = list.stream().collect(Collectors.averagingDouble(StreamStudent::getAge)); // 13.333333333333334
		// 带上以上所有方法
		DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(StreamStudent::getAge));
		System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());
		 
		//分组
		Map<Integer, List<StreamStudent>> ageMap = list.stream().collect(Collectors.groupingBy(StreamStudent::getAge));
		//多重分组,先根据类型分再根据年龄分
		Map<Integer, Map<Integer, List<StreamStudent>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(StreamStudent::getType, Collectors.groupingBy(StreamStudent::getAge)));
		 
		//分区
		//分成两部分，一部分大于10岁，一部分小于等于10岁
		Map<Boolean, List<StreamStudent>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));
		 
		//规约
		Integer allAge = list.stream().map(StreamStudent::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40
	}
	
	private static void testGroup() {
		List<StreamStudent> quoteDetailEntities=Lists.newArrayList(new StreamStudent("aa", 10,1),new StreamStudent("bb", 11,2),new StreamStudent("cc", 14,5),new StreamStudent("cc", 15,6),new StreamStudent("aa", 12,3),new StreamStudent("bb", 13,4));
		Map<String,StreamPerson> checkMap=Maps.newHashMap();
		checkMap.put("aa", new StreamPerson("aa","aaaaaa"));
		checkMap.put("bb", new StreamPerson("bb","bbbbbb"));
		Map<StreamPerson, List<StreamStudent>> vendorMethodQuoteDetailsMap = quoteDetailEntities.stream()
		          .collect(Collectors.groupingBy(quoteDetailEntity -> Optional.ofNullable(checkMap.get(quoteDetailEntity.getName()))
		            .map(billItemEntity -> new StreamPerson(billItemEntity.getName()+"1",billItemEntity.getDescription1())).orElseGet(()->new StreamPerson() )));
		System.out.println(vendorMethodQuoteDetailsMap);
	}

	public static void main(String[] args) {
		testGroup();

	}

}
