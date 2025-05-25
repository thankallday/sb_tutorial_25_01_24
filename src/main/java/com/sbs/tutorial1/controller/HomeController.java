package com.sbs.tutorial1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
//개발자가 스프링부트에게
//이 클래스는 웹 요청을 받아서 작업을 한다
//이 클래스는 Controller 야!!!
public class HomeController
{
    List<Person> personList;
    int num = 0;

    public HomeController()
    {
        personList = new ArrayList<>();
    }

    @GetMapping("/home/main")
    //개발자가 /home/main 이르는 요청을 보내면  아래 메소드를 실행하라.
    //localhost:8080/home/main
    @ResponseBody
    //아래 메소드를 실행해서 body에 출력해라.
    public String showHome()
    {
        return "어서오세요";
    }

    @GetMapping("/home/main2")
    //localhost:8080/home/main2
    @ResponseBody
    public String showHome2()
    {
        return "환영합니다.";
    }

    @GetMapping("/home/main3")
    //localhost:8080/home/main3
    @ResponseBody
    public String showHome3()
    {
        return "스프링부트 획기적이다.";
    }

    @GetMapping("/home/plus")
    //localhost:8080/home/plus?a=1&b=1 --> 2
    //try localhost:8080/home/plus?a=1&bbb=1 --> Error
    //URL 요청시 넘겨준 파라미터 값이 매개변수에 들어간다.
    @ResponseBody
    //public int showPlus(int a, int b) //URL parameter 이름과 매개변수 이름이 일치해야 한다. (스프링부트 규칙)
    //try localhost:8080/home/plus?a=1 --> Error
    //public int showPlus(int a, @RequestParam int b) //localhost:8080/home/plus?a=1 --> Error
    //@RequestParam 생략 가능
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b)
    //localhost:8080/home/plus?a=1 --> good
    //localhost:8080/home/plus?aaa=1 --> 0 --> bad
    //localhost:8080/home/plus?aaa=1&bbb=1 --> 0 --> bad
    {
        return a + b;
    }

    @GetMapping("/home/returnBoolean")
    //localhost:8080/home/returnBoolean --> true
    @ResponseBody
    public boolean showReturnBoolean()
    {
        return true;
    }

    @GetMapping("/home/returnDouble")
    //localhost:8080/home/returnDouble --> 3.141592653589793
    @ResponseBody
    public double showReturnDouble()
    {
        return Math.PI;
    }

    //25 01 24, 스프링부트 기초, 13강, 컨트롤러 클래스에서 다양한 리턴타입을 가진 메소드
    @GetMapping("/home/returnArray")
    //localhost:8080/home/returnArray --> [10,20,30]
    @ResponseBody
    public int[] showReturnArray()
    {
        int[] arr = new int[]{10, 20, 30};
        System.out.println("AAAA 원래는 주소값 arr=" + arr); //--> 원래는 주소값 arr=[I@3a6fa37f
        System.out.println("AAAA array elements arr=" + Arrays.toString(arr)); //--> arr=[10, 20, 30]
        return arr; //--> [10,20,30] --> 스프링부트가 자동으로 JSON 으로 변환시킨다.
    }

    @GetMapping("/home/returnList")
    //localhost:8080/home/returnList --> [10,20,30,40,50,60]
    @ResponseBody
    public List<Integer> showReturnList()
    {
        List<Integer> list = new ArrayList<>(){{
            add(10);
            add(20);
            add(30);
        }};
        List<Integer> list1 = new ArrayList<>();
        list1.add(40);
        list1.add(50);
        list1.add(60);
        list.addAll(list1);
        return list;
    }

    @GetMapping("/home/returnMap")
    /* localhost:8080/home/returnMap
        {
          "id": 1,
          "subject": "스프링부트는 무엇인가?",
          "content": "스프링부트는 무엇이고 어떻게...",
          "articleNo": [1, 2, 3]
        }
    */
    @ResponseBody
    public Map<String, Object> showReturnMap()
    {
        //Map<String, Object> map = new HashMap<>(); //순서 비보장
        //Map<String, Object> map = new LinkedHashMap<>(); //순서 보장
        Map<String, Object> map = new LinkedHashMap<>() {{
            put("id", 1);
            put("subject", "스프링부트는 무엇인가?");
            put("content", "스프링부트는 무엇이고 어떻게...");
            put("articleNo", new ArrayList<>(){{
                add(1);
                add(2);
                add(3);
            }});
        }};
        return map;
    }
    @GetMapping("/home/returnArticle")
    /* localhost:8080/home/returnArticle
        {
          "id": 1,
          "subject": "제목1",
          "content": "내용1",
          "articleNo": [1, 2, 3]
        }
    */
    @ResponseBody
    public Article showReturnArticle()
    {
        Article article = new Article(1, "제목1", "내용1", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});
        return article;
    }

    @GetMapping("/home/returnArticle2")
    /* localhost:8080/home/returnArticle2 --->> lobmok
        {
          "id": 1,
          "subject": "제목1: lombok 사용",
          "content": "내용1: see Article2 class",
          "articleNo": [1, 2, 3]
        }
    */
    @ResponseBody
    public Article2 showReturnArticle2()
    {
        Article2 article = new Article2(1, "제목1: lombok 사용", "내용1: see Article2 class", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});
        return article;
    }

    @GetMapping("/home/returnArticleMapList")
    /* localhost:8080/home/returnArticleMapList
        스프링부트는 JSON 으로 자동 변환시키는 jackson 이라는 라이브러리를 탑재하고 있다.
        [ //배열 시작
          { //객체 시작
            "id": 1,
            "subject": "제목1",
            "content": "내용1",
            "articleNo": [1, 2, 3]
          }, //객체 끝
          {  //객체 시작
            "id": 1,
            "subject": "제목2",
            "content": "내용2",
            "articleNo": [4, 5, 6]
          }  //객체 끝
        ] //배열 끝
    */
    @ResponseBody
    public List<Map<String, Object>> showReturneMapList()
    {
        Map<String, Object> articleMap1 = new LinkedHashMap<>() {{
            put("id", 1);
            put("subject", "제목1");
            put("content", "내용1");
            put("articleNo", new ArrayList<>(){{
                add(1);
                add(2);
                add(3);
            }});
        }};

        Map<String, Object> articleMap2 = new LinkedHashMap<>() {{
            put("id", 1);
            put("subject", "제목2");
            put("content", "내용2");
            put("articleNo", new ArrayList<>(){{
                add(4);
                add(5);
                add(6);
            }});
        }};

        List<Map<String, Object>> list = new ArrayList<>();
        list.add(articleMap1);
        list.add(articleMap2);

        return list;
    }

    @GetMapping("/home/returnArticleList")
    /* localhost:8080/home/returnArticleList
        스프링부트는 JSON 으로 자동 변환시키는 jackson 이라는 라이브러리를 탑재하고 있다.
        원래는 주소가 보내어지는데, 스프링부트는 JSON 으로 자동 변환시키는 jackson 이라는 라이브러리를 탑재하고 있다.
        [
          {
            "id": 1,
            "subject": "제목1: lombok 사용",
            "content": "내용1: see Article2 class",
            "articleNo": [1, 2, 3]
          },
          {
            "id": 1,
            "subject": "제목2: lombok 사용",
            "content": "내용2: see Article2 class",
            "articleNo": [4, 5, 6]
          }
        ]
    */
    @ResponseBody
    public List<Article2> showReturneArticleList()
    {
        Article2 article21 = new Article2(1, "제목1: lombok 사용", "내용1: see Article2 class", new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }});

        Article2 article22 = new Article2(1, "제목2: lombok 사용", "내용2: see Article2 class", new ArrayList<>(){{
            add(4);
            add(5);
            add(6);
        }});

        List<Article2> list = new ArrayList<>();
        list.add(article21);
        list.add(article22);

        return list; //원래는 주소가 보내어지는데, 스프링부트는 JSON 으로 자동 변환시키는 jackson 이라는 라이브러리를 탑재하고 있다.
    }

    //25 02 04, 스프링부트 기초, 15강, 사람을 추가하는 기능을 구현
    @GetMapping("/home/addPerson")
    /*
        localhost:8080/home/addPerson?name=홍길동&age=11 --> 1번 사람이 추가 되었습니다.
        localhost:8080/home/addPerson?name=홍길순&age=22 --> 2번 사람이 추가 되었습니다.
        localhost:8080/home/addPerson?name=임꺽정&age=33 --> 3번 사람이 추가 되었습니다.
        localhost:8080/home/shwPeople
     */
    @ResponseBody
    public String addPerson(String name, int age)
    {
        Person p = new Person(name, age);
        System.out.println("hashCode:" + p.hashCode() + " : " + p);
        personList.add(p);
        return "%d번 사람이 추가 되었습니다".formatted(p.getId());
    }

    @GetMapping("/home/showPeople")
    /*
         [
          {
            "id": 1,
            "name": "홍길동",
            "age": 11
          },
          {"id":2,"name":"홍길동","age":11},{"id":3,"name":"홍길동","age":11},{"id":4,"name":"홍길동","age":11},{"id":5,"name":"홍길동","age":11}
        ]
     */
    @ResponseBody
    public List<Person> showPeople()
    {
        return personList;
    }

    //25 02 04, 스프링부트 기초, 16강, 사람 삭제 기능 구현
    @GetMapping("/home/personTestCase")
    @ResponseBody
    public String personTestCase()
    {
        personList.add(new Person("홍길동", 11));
        personList.add(new Person("홍길순", 22));
        personList.add(new Person("임꺽정", 33));
        return "테스트케이스 추가";
    }

    @GetMapping("/home/removePerson")
    /*
        localhost:8080/home/personTestCase
        localhost:8080/home/showPeople
        localhost:8080/home/removePerson?id=2
        localhost:8080/home/showPeople
        [
          {
            "id": 1,
            "name": "홍길동",
            "age": 11
          },
          {
            "id": 3,
            "name": "임꺽정",
            "age": 33
          }
        ]
     */
    @ResponseBody
    public String removePerson(int id)
    {
//        Person target = null;
//        for (Person p : personList)
//        {
//            if (p.getId() == id)
//            {
//                target = p;
//                break;
//            }
//        }
//
//        if (target == null)
//            return "%d번 사람은 존재하지 않습니다".formatted(id);
//        personList.remove(target);
//        return "%d번 사람이 삭제 되었습니다.".formatted(id);

        //리스트에서 해당 요소가 있으면 삭제
        //삭제가 성공하면 true, 실패하면 false 반환
        boolean removed = personList.removeIf(person -> person.getId() == id);
        return (removed ? "%d번 사람이 삭제 되었습니다.".formatted(id) : "%d번 사람은 존재하지 않습니다".formatted(id));
    }

    //25 02 04, 스프링부트 기초, 17강, 사람 수정 기능 구현
    @GetMapping("/home/modifyPerson")
    /*
        localhost:8080/home/personTestCase
        localhost:8080/home/showPeople
        localhost:8080/home/modifyPerson?id=1&name=홍홍홍&age=44
        localhost:8080/home/modifyPerson?id=2&name=홍길길&age=55
        localhost:8080/home/showPeople
        [{"id":1,"name":"홍홍홍","age":44},
         {"id":2,"name":"홍길길","age":55},
         {"id":3,"name":"임꺽정","age":33}
        ]
     */
    @ResponseBody
    public String modifyPerson(int id, String name, int age)
    {
//        Person target = null;
//        for (Person p : personList)
//        {
//            if (p.getId() == id)
//            {
//                target = p;
//                break;
//            }
//        }
//
//        if (target == null)
//            return "%d번 사람은 존재하지 않습니다".formatted(id);
//        target.setName(name);
//        target.setAge(age);
//        return "%d번 사람이 수정 되었습니다.".formatted(id);

        Person target = personList.stream()
            .filter(p -> p.getId() == id) //해당 녀석이 true 인 것만 필터링
            .findFirst() //찾은 것 중에 하나만 남은 것, 그 남은 것을 필터링
            .orElse(null); //없으면 null을 반환
        /*
          DashboardApiHelper.java
          public static String getLastItemFromRoleUrl(String roleUrl)
          {
            if (roleUrl == null) return null;
            return Arrays.stream(roleUrl.split(",")) //roleUrl 문자열을 쉼표를 구분 기호로 사용하여 부분 문자열 배열로 분할합니다.
                                                     //그런 다음 Arrays.stream()메서드는 이 배열에서 스트림을 생성합니다.
                .map(String::trim) //각 요소(부분 문자열)는 앞뒤 공백을 제거합니다
                                   //Each element in the stream (substring) is trimmed to remove leading and trailing whitespace
                .reduce((first, second) -> second) //selects the last element of the stream.
                .orElse(null) //returning null if no elements are present
                .replaceAll("[)';]", "")
                .trim();
          }
        */

        if (target == null)
            return "%d번 사람은 존재하지 않습니다".formatted(id);
        target.setName(name);
        target.setAge(age);
        return "%d번 사람이 수정 되었습니다.".formatted(id);
    }

    //25 02 04, 스프링부트 기초, 18강, 스프링부트는 고객을 구분하지 못한다.
    //25 02 04, 스프링부트 기초, 19강, 스프링부트 서버는 고객(클라이언트, 웹브라우저)를 기억하지 못하기 때문에, 쿠키를 이용하여 개별처리를 할 수 있다.
    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease()
    {
        num++;
        return num;
    }
    @GetMapping("/home/reqandresp")
    /*
        localhost:8080/home/reqandresp?age=20 --> Hello, I'm 20 years old.
     */
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        int age = Integer.parseInt(req.getParameter("age"));
        res.getWriter().append("Hello, I'm %d years old.".formatted(age));
    }
    @GetMapping("/home/cookie/increase")
    /*
        localhost:8080/home/cookie/increase
        F10 --> See "count" cookie in the browser.
     */
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse res)
    {
        int countInCookie =0;
        if (req.getCookies() != null) //요청에 쿠키가 없으면
        {
            countInCookie = Arrays.stream(req.getCookies()) //요청에 있는 쿠키들을 스트림으로 변환
                .filter(c -> c.getName().equals("count")) //쿠키 이름이 count 인 것만 필터링
                .map(Cookie::getValue) //필터링된 쿠키의 값을 가져온다.
                .mapToInt(Integer::parseInt) //문자열을 정수로 변환
                .findFirst() //필터링된 것 중에 첫번째를 찾는다.
                .orElse(0); //없으면 0을 반환
        }
        int newCountInCookie = ++countInCookie;
        res.addCookie(new Cookie("count", newCountInCookie + "")); //쿠키가 없으면 count 쿠키를 생성

        return newCountInCookie;
    }

    class Article
    {
        private final int id;
        private String subject;
        private  String content;
        private List<Integer> articleNo;

        public Article(int id, String subject, String content, List<Integer> articleNo)
        {
            this.id = id;
            this.subject = subject;
            this.content = content;
            this.articleNo = articleNo;
        }

        public int getId()
        {
            return id;
        }

        public String getSubject()
        {
            return subject;
        }

        public void setSubject(String subject)
        {
            this.subject = subject;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public List<Integer> getArticleNo()
        {
            return articleNo;
        }

        public void setArticleNo(List<Integer> articleNo)
        {
            this.articleNo = articleNo;
        }

        @Override
        public String toString()
        {
            return "Article{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", articleNo=" + articleNo +
                '}';
        }
    }

    //lombok - getter, setter, constructor, toString을 자동으로 대체하여 코드가 간단해 진다.
    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    class Article2
    {
        private final int id;
        private String subject;
        private String content;
        private List<Integer> articleNo;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    class Person
    {
        private static  int lastId;
        private final int id;
        private String name;
        private int age;

        static
        {
            lastId = 0;
        }
        public Person(String name, int age)
        {
            this(++lastId, name, age);
        }
    }
}
