package com.core.miniproject.src.accommodation.domain.entity;

import com.core.miniproject.src.accommodation.domain.dto.AccommodationRequest;
import com.core.miniproject.src.image.domain.entity.AccommodationImage;
import com.core.miniproject.src.location.domain.entity.Location;
import com.core.miniproject.src.member.domain.entity.Member;
import com.core.miniproject.src.rate.domain.entity.Rate;
import com.core.miniproject.src.room.domain.entity.Room;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql="update Accommodation set is_deleted=true where accommodation_id=?")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    //location_id 참조 관계 설정
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Location location;

    //room_id 참조 관계 설정
    @Builder.Default
    @OneToMany(mappedBy = "accommodationId", cascade = CascadeType.REMOVE)
    @Column(name = "room_id")
    private Set<Room> roomId = new HashSet<>();

    @Column(name="introduction")
    private String introduction;

    @Column(name="accommodation_name")
    private String accommodationName;

    @Column(name="accommodation_type")
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodationType;

    @Builder.Default
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private Set<Rate> rates = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Discount discount;

    @Column(name = "address")
    private String address;

    @Column(name="is_deleted")
    @Builder.Default
    private boolean isDeleted=false;

    @Builder.Default
    @OneToMany(mappedBy = "accommodation")
    private List<AccommodationImage> images = new ArrayList<>();

//    public Double getRate() {
//        return this.getRates() != null ? this.getRate() : 0.0; // 기본값으로 0.0을 반환하도록 수정
//    }

    public Double getAverageRate() {
        if (rates == null || rates.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Rate rate : rates) {
            sum += rate.getRate();
        }

        return Math.ceil(sum / rates.size() * 100.0) / 100.0;
    }

    public Integer getMinPrice() {
        if (roomId == null || roomId.isEmpty()) { // 객실이 없기 때문에 표시될 minPrice가 정해지지 않음
            return 0;
        }

        Integer minPrice = Integer.MAX_VALUE;

        for(Room room : roomId) {
            if(room.getPrice() < minPrice) {
                minPrice = room.getPrice();
            }
        }
        return minPrice;
    }

    public void update(AccommodationRequest request, Location location, Discount discount ,List<AccommodationImage> images){
        this.accommodationType = AccommodationType.getByText(request.getAccommodationType());
        this.accommodationName = request.getAccommodationName();
        this.introduction = request.getIntroduction();
        this.address = request.getAddress();
        this.images = images;
        this.location = location;
        this.discount = discount;
    }

    public void assignImages(List<AccommodationImage> images){
        this.images = images;
    }
}
