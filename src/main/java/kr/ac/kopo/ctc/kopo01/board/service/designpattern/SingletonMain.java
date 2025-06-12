package kr.ac.kopo.ctc.kopo01.board.service.designpattern;

/**
 * Singleton 클래스
 * - 인스턴스를 하나만 생성하고 어디서든 동일한 객체를 공유하도록 보장하는 패턴
 */
class Singleton {
    // static 변수로 하나의 인스턴스를 메모리에 유지
    private static Singleton instance;

    // 외부에서 new로 생성하지 못하게 생성자 private 처리
    private Singleton() {}

    // 외부에서 인스턴스를 요청할 수 있도록 public static 메서드로 수정
    public static Singleton getInstance() {
        // 처음 요청 시에만 인스턴스 생성
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
/**
 * SingletonMain 클래스
 * - Singleton 패턴이 잘 작동하는지 테스트
 */
public class SingletonMain {
    public static void main(String[] args) {
        // 세 번 호출해도 같은 인스턴스 반환됨
        Singleton i1 = Singleton.getInstance();
        Singleton i2 = Singleton.getInstance();
        Singleton i3 = Singleton.getInstance();

        // 주소값 출력: 전부 동일함
        System.out.println(i1.toString());
        System.out.println(i2.toString());
        System.out.println(i3.toString());

        // i1, i2, i3가 모두 같은 인스턴스인지 비교 → true
        System.out.println(i1 == i2);
    }
}
