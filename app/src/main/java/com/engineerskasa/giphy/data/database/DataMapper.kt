package com.engineerskasa.giphy.data.database

import com.engineerskasa.giphy.model.Data
import com.engineerskasa.giphy.model.FixedHeightSmallStill
import com.engineerskasa.giphy.model.Images

fun DataEntity.toData() = Data(
    Images(FixedHeightSmallStill(this.images, "320", "420")),
    this.title,
    this.type,
    this.username
)

fun List<DataEntity>.toDataList() = this.map { it.toData() }

fun Data.toDataEntity() = DataEntity(
    images = this.images.fixedHeightSmallStill.url,
    title = this.title,
    type = this.type,
    username = this.username
)

fun List<Data>.toDataEntityList() = this.map { it.toDataEntity() }