package kr.ac.kopo.ctc.kopo01.board.aop;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 📌 CacheConfig 클래스
 *
 * 이 클래스는 Spring Framework에서 제공하는 캐시 추상화 기능을 활성화하기 위한 설정 클래스입니다.
 *
 * ✅ 주요 기능:
 * 1. @EnableCaching:
 *    - Spring의 캐시 기능을 활성화합니다.
 *    - @Cacheable, @CachePut, @CacheEvict 등의 어노테이션을 사용할 수 있게 만듭니다.
 *    - 메소드의 실행 결과를 캐시에 저장하고, 동일한 요청 시 캐시된 결과를 반환해 성능을 향상시킵니다.
 *
 * 2. @Configuration:
 *    - 이 클래스가 스프링의 설정 클래스(Bean 설정 파일)임을 나타냅니다.
 *    - 이 클래스를 통해 캐시 기능에 관련된 설정을 Bean으로 등록할 수 있습니다.
 *
 * ✅ 예를 들어 다음과 같은 사용이 가능합니다:
 *
 *   @Cacheable(value = "posts", key = "#id")
 *   public Post getPostById(Long id) {
 *       // DB에서 조회
 *       return postRepository.findById(id).orElseThrow(...);
 *   }
 *
 * 🔧 확장 가능성:
 * - SimpleCacheManager, ConcurrentMapCacheManager, RedisCacheManager 등 캐시 구현체를 명시적으로 등록할 수 있습니다.
 * - 필요 시 캐시 TTL(Time-To-Live), key generator, custom serializer 등의 설정도 이 클래스에서 구성할 수 있습니다.
 */
@Configuration  // 이 클래스가 스프링 설정 클래스임을 나타냄 (빈 정의 가능)
@EnableCaching  // 스프링에서 캐시 기능(@Cacheable 등)을 활성화함
public class CacheConfig {
    // 현재는 기본 설정이지만, 필요에 따라 CacheManager bean 등을 정의할 수 있음
}
