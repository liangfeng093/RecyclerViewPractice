package com.liangfeng.recyclerviewpractice

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by mzf on 2018/9/18.
 * Email:liangfeng093@gmail.com
 * Desc:
 */
class GalleryAdapter : BaseQuickAdapter<Int, BaseViewHolder> {

    constructor(layoutResId: Int, data: MutableList<Int>?) : super(layoutResId, data)

    override fun convert(helper: BaseViewHolder?, item: Int?) {

        helper?.setImageResource(R.id.image, item!!)
    }

}