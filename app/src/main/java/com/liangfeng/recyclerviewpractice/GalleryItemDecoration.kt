package com.liangfeng.recyclerviewpractice

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.Utils

/**
 * Created by mzf on 2018/9/18.
 * Email:liangfeng093@gmail.com
 * Desc:
 */
class GalleryItemDecoration : RecyclerView.ItemDecoration() {
    val TAG = this.javaClass.name
    var mPageMargin = 0f //每一个页面默认页边距
    var mLeftPageVisibleWidth = 60f//中间页面左右两边的页面可见部分宽度
    var mItemComusemX = 0f //一页理论消耗距离

    var leftMargin = 0
    var rightMargin = 0
    var itemNewWidth = 0

    var mOnItemSizeMeasuredListener: OnItemSizeMeasuredListener? = null


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        Log.e(TAG, ">>>>>>>:getItemOffsets")

        //让第0位置的图片左边保持和其他图片一样的距离，那么就需要动态设置第0位置图片的左边距为2倍页边距 + 可视距离。同理，最后一张也是做同样的操作

        var itemCount = parent?.adapter?.itemCount

        var lastPosition = state?.getItemCount()!! - 1 //整个RecyclerView最后一个item的position
        var currentPosition = parent?.getChildLayoutPosition(view) //获取当前要进行布局的item的position

        //确保view的宽高计算完毕
        parent?.post {
            // 动态修改页面的宽度
            itemNewWidth = parent?.width!! - ConvertUtils.dp2px(4 * mPageMargin + 2 * mLeftPageVisibleWidth)
            // 一页理论消耗距离
            mItemComusemX = (itemNewWidth + ConvertUtils.dp2px(2 * mPageMargin)).toFloat()

//         第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
            leftMargin = if (currentPosition == 0) {
                ConvertUtils.dp2px(2 * mPageMargin + mLeftPageVisibleWidth)
            } else {
                ConvertUtils.dp2px(mPageMargin)
            }

            rightMargin = if (currentPosition == lastPosition) {
                ConvertUtils.dp2px(2 * mPageMargin + mLeftPageVisibleWidth)
            } else {
                ConvertUtils.dp2px(mPageMargin)
            }

            Log.e(TAG, ">>>>>>>leftMargin:" + leftMargin)
            Log.e(TAG, ">>>>>>>rightMargin:" + rightMargin)
            //设置参数
            var param = view?.layoutParams as RecyclerView.LayoutParams
            param?.setMargins(leftMargin, 0, rightMargin, 0)
            param?.width = itemNewWidth
            view?.layoutParams = param
            Log.e(TAG, ">>>>>>>rightMargin1:" + (view?.layoutParams as RecyclerView.LayoutParams)?.rightMargin)
            Log.e(TAG, ">>>>>>>leftMargin1:" + (view?.layoutParams as RecyclerView.LayoutParams)?.leftMargin)

        }
    }

    var flag = true

    interface OnItemSizeMeasuredListener {
        /**
         * Item的大小测量完成
         * @param size int
         */
        fun onItemSizeMeasured(size: Int)
    }
}