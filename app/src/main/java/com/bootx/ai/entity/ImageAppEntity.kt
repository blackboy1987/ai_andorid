package com.bootx.ai.entity

data class ImageAppEntity(
    val modelList: List<ModelList> = listOf(),
    val imageVariation: ImageVariation= ImageVariation(),
    val textToImage: TextToImage= TextToImage(),
    val styleTransfer: StyleTransfer= StyleTransfer(),
)
data class TextToImage(
    val styleList: List<StyleList> = listOf(),
    val styleCatalogDTOList: List<StyleCatalog> = listOf(),
    val samples: List<Sample> = listOf(),
    val resolutions: List<String> = listOf(),
)
data class StyleList(
    val code: String="",
    val name: String="",
    val pic: String="",
    val styleType: String="",
    val realPics: List<String> = listOf(),
)
data class StyleCatalog(
    val type: String="",
    val styleType: String="",
    val dataList: List<DataList> = listOf(),
)
data class DataList(
    val code: String="",
    val name: String="",
    val pic: String="",
    val styleType: String="",
)

data class Sample(
    val title: String="",
)





data class StyleTransfer(
    val styleList: List<StyleList> = listOf(),
)






data class ImageVariation(
    val styleList: List<StyleList> = listOf(),
)


data class ModelList(
    val modelId:Int = 0,
    val modelName: String="",
    val description: String="",
    val cover: String="",
    val category: String="",
    val source: String="",
    val scope: String="",
    val platform: String="",
    val trainParams: TrainParams=TrainParams(),
    val trainModel: String="",
    val trainType: String="",
    var recommendPrompt: List<String> = listOf(),
)
data class TrainParams(
    val promptPrefix: String="",
)

data class ImageAppEntityResponse(val data: ImageAppEntity) : BaseResponse()