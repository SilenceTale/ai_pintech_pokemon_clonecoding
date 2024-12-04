package org.koreait.pokemon.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPokemon is a Querydsl query type for Pokemon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPokemon extends EntityPathBase<Pokemon> {

    private static final long serialVersionUID = -16831100L;

    public static final QPokemon pokemon = new QPokemon("pokemon");

    public final org.koreait.global.entities.QBaseEntity _super = new org.koreait.global.entities.QBaseEntity(this);

    public final StringPath abilities = createString("abilities");

    public final NumberPath<Integer> baseExperience = createNumber("baseExperience", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath flavorText = createString("flavorText");

    public final StringPath frontImage = createString("frontImage");

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath nameEn = createString("nameEn");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath types = createString("types");

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QPokemon(String variable) {
        super(Pokemon.class, forVariable(variable));
    }

    public QPokemon(Path<? extends Pokemon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPokemon(PathMetadata metadata) {
        super(Pokemon.class, metadata);
    }

}

