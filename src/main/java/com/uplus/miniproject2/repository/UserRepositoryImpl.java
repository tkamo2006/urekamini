package com.uplus.miniproject2.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.uplus.miniproject2.dto.MainPageUserDto;
import com.uplus.miniproject2.entity.proflie.MBTI;
import com.uplus.miniproject2.entity.proflie.QProfile;
import com.uplus.miniproject2.entity.user.QUser;
import com.uplus.miniproject2.entity.user.User;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements CustomUserRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MainPageUserDto> findBySearchCondition(String name, String mbti, String major, String gender,
                                                       Pageable pageable) {
        QUser user = QUser.user;
        QProfile profile = QProfile.profile;

        BooleanBuilder builder = new BooleanBuilder();

        if (name != null && !name.isEmpty()) {
            builder.and(user.name.containsIgnoreCase(name));
        }

        if (mbti != null && !mbti.isEmpty()) {
            builder.and(profile.mbti.eq(MBTI.valueOf(mbti)));
        }

        if (major != null && !major.isEmpty()) {
            builder.and(profile.major.containsIgnoreCase(major));
        }

        if (gender != null && !gender.isEmpty()) {
            builder.and(user.gender.eq(gender));
        }

        List<MainPageUserDto> result = queryFactory
                .select(Projections.constructor(MainPageUserDto.class,
                        user.name,
                        user.id,
                        profile.major,
                        profile.mbti.stringValue(),
                        profile.region.name,
                        profile.image
                ))
                .from(user)
                .join(user.profile, profile)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(user)
                .join(user.profile, profile)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public User findById(Long userId) {
        QUser user = QUser.user;

        return queryFactory.selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }
}
