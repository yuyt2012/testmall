package io.ecp.testmall.delivery.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryAddress is a Querydsl query type for DeliveryAddress
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeliveryAddress extends BeanPath<DeliveryAddress> {

    private static final long serialVersionUID = 1288276683L;

    public static final QDeliveryAddress deliveryAddress = new QDeliveryAddress("deliveryAddress");

    public final StringPath receiverCity = createString("receiverCity");

    public final StringPath receiverStreet = createString("receiverStreet");

    public final StringPath receiverZipcode = createString("receiverZipcode");

    public QDeliveryAddress(String variable) {
        super(DeliveryAddress.class, forVariable(variable));
    }

    public QDeliveryAddress(Path<? extends DeliveryAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryAddress(PathMetadata metadata) {
        super(DeliveryAddress.class, metadata);
    }

}

