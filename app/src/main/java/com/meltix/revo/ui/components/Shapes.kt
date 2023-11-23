package com.meltix.revo.ui.components

import android.graphics.Matrix
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


val Clam6Shape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 83.3132f
        val baseHeight = 92.1769f
        
        val path = Path().apply {
            moveTo(33.3418f, 3.0068f)
            cubicTo(38.1601f, -1.0023f, 45.1531f, -1.0023f, 49.9714f, 3.0068f)
            lineTo(59.4373f, 10.8829f)
            cubicTo(60.5751f, 11.8296f, 61.8663f, 12.5751f, 63.2552f, 13.0872f)
            lineTo(74.809f, 17.3468f)
            cubicTo(80.6901f, 19.515f, 84.1866f, 25.5711f, 83.1238f, 31.7485f)
            lineTo(81.0359f, 43.8842f)
            cubicTo(80.7849f, 45.343f, 80.7849f, 46.8339f, 81.0359f, 48.2927f)
            lineTo(83.1238f, 60.4284f)
            cubicTo(84.1866f, 66.6058f, 80.6901f, 72.6619f, 74.809f, 74.8301f)
            lineTo(63.2552f, 79.0897f)
            cubicTo(61.8663f, 79.6018f, 60.5751f, 80.3473f, 59.4373f, 81.294f)
            lineTo(49.9714f, 89.1701f)
            cubicTo(45.1531f, 93.1792f, 38.1601f, 93.1792f, 33.3418f, 89.1701f)
            lineTo(23.8759f, 81.294f)
            cubicTo(22.7381f, 80.3473f, 21.4469f, 79.6018f, 20.058f, 79.0897f)
            lineTo(8.5042f, 74.8301f)
            cubicTo(2.6231f, 72.6619f, -0.8734f, 66.6058f, 0.1894f, 60.4284f)
            lineTo(2.2774f, 48.2927f)
            cubicTo(2.5284f, 46.8339f, 2.5284f, 45.343f, 2.2774f, 43.8842f)
            lineTo(0.1894f, 31.7485f)
            cubicTo(-0.8734f, 25.5712f, 2.6231f, 19.515f, 8.5042f, 17.3468f)
            lineTo(20.058f, 13.0872f)
            cubicTo(21.4469f, 12.5751f, 22.7381f, 11.8296f, 23.8759f, 10.8829f)
            lineTo(33.3418f, 3.0068f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val Clam8Shape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 94.847f
        val baseHeight = 94.847f
        
        val path = Path().apply {
            moveTo(40.2621f, 2.1503f)
            cubicTo(44.6059f, -0.7168f, 50.241f, -0.7168f, 54.5848f, 2.1503f)
            lineTo(61.5929f, 6.7761f)
            cubicTo(62.9766f, 7.6893f, 64.5221f, 8.3295f, 66.1462f, 8.6621f)
            lineTo(74.3726f, 10.3467f)
            cubicTo(79.4715f, 11.3909f, 83.4561f, 15.3755f, 84.5003f, 20.4744f)
            lineTo(86.1849f, 28.7007f)
            cubicTo(86.5175f, 30.3249f, 87.1576f, 31.8704f, 88.0709f, 33.254f)
            lineTo(92.6966f, 40.2621f)
            cubicTo(95.5637f, 44.6059f, 95.5637f, 50.241f, 92.6966f, 54.5848f)
            lineTo(88.0709f, 61.5929f)
            cubicTo(87.1576f, 62.9766f, 86.5175f, 64.5221f, 86.1849f, 66.1462f)
            lineTo(84.5003f, 74.3726f)
            cubicTo(83.4561f, 79.4715f, 79.4715f, 83.4561f, 74.3726f, 84.5003f)
            lineTo(66.1462f, 86.1849f)
            cubicTo(64.5221f, 86.5175f, 62.9766f, 87.1576f, 61.5929f, 88.0709f)
            lineTo(54.5848f, 92.6966f)
            cubicTo(50.241f, 95.5637f, 44.6059f, 95.5637f, 40.2621f, 92.6966f)
            lineTo(33.254f, 88.0709f)
            cubicTo(31.8704f, 87.1576f, 30.3249f, 86.5175f, 28.7007f, 86.1849f)
            lineTo(20.4744f, 84.5003f)
            cubicTo(15.3755f, 83.4561f, 11.3909f, 79.4715f, 10.3467f, 74.3726f)
            lineTo(8.6621f, 66.1462f)
            cubicTo(8.3295f, 64.5221f, 7.6893f, 62.9766f, 6.7761f, 61.5929f)
            lineTo(2.1503f, 54.5848f)
            cubicTo(-0.7168f, 50.241f, -0.7168f, 44.6059f, 2.1503f, 40.2621f)
            lineTo(6.7761f, 33.254f)
            cubicTo(7.6893f, 31.8704f, 8.3295f, 30.3249f, 8.6621f, 28.7007f)
            lineTo(10.3467f, 20.4744f)
            cubicTo(11.3909f, 15.3755f, 15.3755f, 11.3909f, 20.4744f, 10.3467f)
            lineTo(28.7007f, 8.6621f)
            cubicTo(30.3249f, 8.3295f, 31.8704f, 7.6893f, 33.254f, 6.7761f)
            lineTo(40.2621f, 2.1503f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val Clam10Shape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 90.5002f
        val baseHeight = 93.8177f
        
        val path = Path().apply {
            moveTo(37.5889f, 2.4973f)
            cubicTo(42.1537f, -0.8324f, 48.3466f, -0.8324f, 52.9113f, 2.4973f)
            lineTo(54.9487f, 3.9835f)
            cubicTo(57.1803f, 5.6114f, 59.8724f, 6.4861f, 62.6347f, 6.4808f)
            lineTo(65.1565f, 6.476f)
            cubicTo(70.8067f, 6.4653f, 75.8168f, 10.1054f, 77.5526f, 15.4823f)
            lineTo(78.3273f, 17.8822f)
            cubicTo(79.1759f, 20.5109f, 80.8397f, 22.8009f, 83.0775f, 24.4203f)
            lineTo(85.1205f, 25.8987f)
            cubicTo(89.6979f, 29.2111f, 91.6116f, 35.1009f, 89.8554f, 40.4712f)
            lineTo(89.0715f, 42.8681f)
            cubicTo(88.213f, 45.4935f, 88.213f, 48.3242f, 89.0715f, 50.9496f)
            lineTo(89.8554f, 53.3465f)
            cubicTo(91.6116f, 58.7168f, 89.6979f, 64.6066f, 85.1205f, 67.919f)
            lineTo(83.0775f, 69.3974f)
            cubicTo(80.8397f, 71.0167f, 79.1759f, 73.3068f, 78.3273f, 75.9355f)
            lineTo(77.5526f, 78.3353f)
            cubicTo(75.8168f, 83.7123f, 70.8067f, 87.3524f, 65.1565f, 87.3416f)
            lineTo(62.6347f, 87.3368f)
            cubicTo(59.8724f, 87.3316f, 57.1803f, 88.2063f, 54.9487f, 89.8342f)
            lineTo(52.9113f, 91.3203f)
            cubicTo(48.3466f, 94.6501f, 42.1537f, 94.6501f, 37.5889f, 91.3203f)
            lineTo(35.5515f, 89.8342f)
            cubicTo(33.3199f, 88.2063f, 30.6278f, 87.3316f, 27.8656f, 87.3368f)
            lineTo(25.3438f, 87.3416f)
            cubicTo(19.6936f, 87.3524f, 14.6834f, 83.7123f, 12.9476f, 78.3353f)
            lineTo(12.1729f, 75.9355f)
            cubicTo(11.3243f, 73.3068f, 9.6605f, 71.0167f, 7.4227f, 69.3974f)
            lineTo(5.3797f, 67.919f)
            cubicTo(0.8023f, 64.6066f, -1.1114f, 58.7168f, 0.6448f, 53.3465f)
            lineTo(1.4287f, 50.9496f)
            cubicTo(2.2873f, 48.3242f, 2.2873f, 45.4935f, 1.4287f, 42.8681f)
            lineTo(0.6448f, 40.4712f)
            cubicTo(-1.1114f, 35.1009f, 0.8023f, 29.2111f, 5.3797f, 25.8987f)
            lineTo(7.4227f, 24.4203f)
            cubicTo(9.6605f, 22.8009f, 11.3243f, 20.5109f, 12.1729f, 17.8822f)
            lineTo(12.9476f, 15.4823f)
            cubicTo(14.6834f, 10.1054f, 19.6936f, 6.4653f, 25.3438f, 6.476f)
            lineTo(27.8656f, 6.4808f)
            cubicTo(30.6278f, 6.4861f, 33.3199f, 5.6114f, 35.5515f, 3.9835f)
            lineTo(37.5889f, 2.4973f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val Clam12Shape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 93.5004f
        val baseHeight = 93.5004f
        
        val path = Path().apply {
            moveTo(39.6563f, 2.521f)
            cubicTo(43.7883f, -0.8403f, 49.7121f, -0.8403f, 53.8441f, 2.521f)
            cubicTo(56.3291f, 4.5425f, 59.5585f, 5.4078f, 62.7213f, 4.8996f)
            cubicTo(67.9804f, 4.0546f, 73.1106f, 7.0165f, 75.0083f, 11.9935f)
            cubicTo(76.1497f, 14.9867f, 78.5137f, 17.3508f, 81.5069f, 18.4921f)
            cubicTo(86.4839f, 20.3899f, 89.4458f, 25.52f, 88.6008f, 30.7791f)
            cubicTo(88.0926f, 33.9419f, 88.958f, 37.1713f, 90.9795f, 39.6563f)
            cubicTo(94.3408f, 43.7883f, 94.3408f, 49.7121f, 90.9795f, 53.8441f)
            cubicTo(88.958f, 56.3291f, 88.0926f, 59.5585f, 88.6008f, 62.7213f)
            cubicTo(89.4458f, 67.9804f, 86.4839f, 73.1106f, 81.5069f, 75.0083f)
            cubicTo(78.5137f, 76.1497f, 76.1497f, 78.5137f, 75.0083f, 81.5069f)
            cubicTo(73.1106f, 86.4839f, 67.9804f, 89.4458f, 62.7213f, 88.6008f)
            cubicTo(59.5585f, 88.0926f, 56.3291f, 88.958f, 53.8441f, 90.9795f)
            cubicTo(49.7121f, 94.3408f, 43.7883f, 94.3408f, 39.6563f, 90.9795f)
            cubicTo(37.1713f, 88.958f, 33.9419f, 88.0926f, 30.7791f, 88.6008f)
            cubicTo(25.52f, 89.4458f, 20.3899f, 86.4839f, 18.4921f, 81.5069f)
            cubicTo(17.3508f, 78.5137f, 14.9867f, 76.1497f, 11.9935f, 75.0083f)
            cubicTo(7.0165f, 73.1106f, 4.0546f, 67.9804f, 4.8996f, 62.7213f)
            cubicTo(5.4078f, 59.5585f, 4.5425f, 56.3291f, 2.521f, 53.8441f)
            cubicTo(-0.8403f, 49.7121f, -0.8403f, 43.7883f, 2.521f, 39.6563f)
            cubicTo(4.5425f, 37.1713f, 5.4078f, 33.9419f, 4.8996f, 30.7791f)
            cubicTo(4.0546f, 25.52f, 7.0165f, 20.3899f, 11.9935f, 18.4921f)
            cubicTo(14.9867f, 17.3508f, 17.3508f, 14.9867f, 18.4921f, 11.9935f)
            cubicTo(20.3899f, 7.0165f, 25.52f, 4.0546f, 30.7791f, 4.8996f)
            cubicTo(33.9419f, 5.4078f, 37.1713f, 4.5425f, 39.6563f, 2.521f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val Squircle45Shape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 113.2548f
        val baseHeight = 113.2548f
        
        val path = Path().apply {
            moveTo(21.2721f, 91.9828f)
            cubicTo(10.7335f, 81.4442f, 5.4642f, 76.1749f, 2.897f, 70.3607f)
            cubicTo(-0.9657f, 61.6126f, -0.9657f, 51.6422f, 2.897f, 42.8941f)
            cubicTo(5.4642f, 37.0799f, 10.7335f, 31.8107f, 21.2721f, 21.2721f)
            cubicTo(31.8107f, 10.7335f, 37.0799f, 5.4642f, 42.8941f, 2.897f)
            cubicTo(51.6422f, -0.9657f, 61.6126f, -0.9657f, 70.3607f, 2.897f)
            cubicTo(76.1749f, 5.4642f, 81.4442f, 10.7335f, 91.9828f, 21.2721f)
            cubicTo(102.5213f, 31.8107f, 107.7906f, 37.0799f, 110.3578f, 42.8941f)
            cubicTo(114.2205f, 51.6422f, 114.2205f, 61.6126f, 110.3578f, 70.3607f)
            cubicTo(107.7906f, 76.1749f, 102.5213f, 81.4442f, 91.9828f, 91.9828f)
            cubicTo(81.4442f, 102.5213f, 76.1749f, 107.7906f, 70.3607f, 110.3578f)
            cubicTo(61.6126f, 114.2205f, 51.6422f, 114.2205f, 42.8941f, 110.3578f)
            cubicTo(37.0799f, 107.7906f, 31.8107f, 102.5213f, 21.2721f, 91.9828f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val HexagonShape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 86.6025f
        val baseHeight = 95.359f
        
        val path = Path().apply {
            moveTo(35.8013f, 2.0096f)
            cubicTo(40.4423f, -0.6699f, 46.1603f, -0.6699f, 50.8013f, 2.0096f)
            lineTo(79.1025f, 18.3494f)
            cubicTo(83.7436f, 21.0289f, 86.6025f, 25.9808f, 86.6025f, 31.3397f)
            lineTo(86.6025f, 64.0192f)
            cubicTo(86.6025f, 69.3782f, 83.7436f, 74.3301f, 79.1025f, 77.0096f)
            lineTo(50.8013f, 93.3494f)
            cubicTo(46.1603f, 96.0289f, 40.4423f, 96.0289f, 35.8013f, 93.3494f)
            lineTo(7.5f, 77.0096f)
            cubicTo(2.859f, 74.3301f, 0f, 69.3782f, 0f, 64.0192f)
            lineTo(0f, 31.3397f)
            cubicTo(0f, 25.9808f, 2.859f, 21.0289f, 7.5f, 18.3494f)
            lineTo(35.8013f, 2.0096f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val SoftStarShape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 82.4178f
        val baseHeight = 82.5617f
        
        val path = Path().apply {
            moveTo(0.7834f, 23.1961f)
            cubicTo(-3.3231f, 9.5077f, 9.3929f, -3.2772f, 23.1033f, 0.7552f)
            lineTo(37.0572f, 4.8593f)
            cubicTo(40.4377f, 5.8536f, 44.0357f, 5.8338f, 47.405f, 4.8024f)
            lineTo(59.0594f, 1.2347f)
            cubicTo(72.8103f, -2.9748f, 85.7015f, 9.8443f, 81.5692f, 23.6185f)
            lineTo(77.7913f, 36.2113f)
            cubicTo(76.7792f, 39.5852f, 76.7792f, 43.182f, 77.7913f, 46.5559f)
            lineTo(81.634f, 59.3648f)
            cubicTo(85.7532f, 73.0953f, 72.9514f, 85.8971f, 59.2209f, 81.778f)
            lineTo(46.4119f, 77.9353f)
            cubicTo(43.038f, 76.9231f, 39.4413f, 76.9231f, 36.0674f, 77.9353f)
            lineTo(23.2584f, 81.778f)
            cubicTo(9.5279f, 85.8971f, -3.2739f, 73.0953f, 0.8453f, 59.3648f)
            lineTo(4.688f, 46.5559f)
            cubicTo(5.7002f, 43.182f, 5.7002f, 39.5852f, 4.688f, 36.2113f)
            lineTo(0.7834f, 23.1961f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}
val SquircleShape: Shape = object: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val baseWidth = 100f
        val baseHeight = 100f
        
        val path = Path().apply {
            moveTo(0f, 50f)
            cubicTo(0f, 35.0962f, 0f, 27.6443f, 2.2959f, 21.7178f)
            cubicTo(5.7505f, 12.8006f, 12.8006f, 5.7505f, 21.7178f, 2.2959f)
            cubicTo(27.6443f, 0f, 35.0962f, 0f, 50f, 0f)
            cubicTo(64.9038f, 0f, 72.3557f, 0f, 78.2822f, 2.2959f)
            cubicTo(87.1994f, 5.7505f, 94.2495f, 12.8006f, 97.7041f, 21.7178f)
            cubicTo(100f, 27.6443f, 100f, 35.0962f, 100f, 50f)
            cubicTo(100f, 64.9038f, 100f, 72.3557f, 97.7041f, 78.2822f)
            cubicTo(94.2495f, 87.1994f, 87.1994f, 94.2495f, 78.2822f, 97.7041f)
            cubicTo(72.3557f, 100f, 64.9038f, 100f, 50f, 100f)
            cubicTo(35.0962f, 100f, 27.6443f, 100f, 21.7178f, 97.7041f)
            cubicTo(12.8006f, 94.2495f, 5.7505f, 87.1994f, 2.2959f, 78.2822f)
            cubicTo(0f, 72.3557f, 0f, 64.9038f, 0f, 50f)
            close()
        }
        
        return Outline.Generic(
            path
                .asAndroidPath()
                .apply {
                    transform(Matrix().apply {
                        setScale(size.width / baseWidth, size.height / baseHeight)
                    })
                }
                .asComposePath()
        )
    }
}