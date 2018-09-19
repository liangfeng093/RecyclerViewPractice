package com.liangfeng.recyclerviewpractice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_gallery.*

/**
 * Created by mzf on 2018/9/18.
 * Email:liangfeng093@gmail.com
 * Desc:
 */
class GalleryActivity : AppCompatActivity() {
    val TAG = this.javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

//        var list = arrayListOf(R.mipmap.timg, R.mipmap.timg2, R.mipmap.timg3, R.mipmap.timg4, R.mipmap.timg5)

        mItemDecoration = GalleryItemDecoration()
        mRecyclerView?.addItemDecoration(mItemDecoration)

        var list = arrayListOf(R.mipmap.heizi1, R.mipmap.heizi2, R.mipmap.heizi3, R.mipmap.heizi4, R.mipmap.heizi5
                , R.mipmap.heizi6, R.mipmap.heizi7, R.mipmap.heizi8, R.mipmap.heizi9, R.mipmap.heizi10)
        var data = mutableListOf<Int>()
        data?.addAll(list)
        mRecyclerView?.adapter = GalleryAdapter(R.layout.item_gallery, data)
        //第二个参数就是用于指定方向是竖直还是水平，第三个参数用于指定是否从右到左布局，基本都是false，我们的习惯都是左到右的排列方式
        mRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var snapHelper = LinearSnapHelper()
        snapHelper?.attachToRecyclerView(mRecyclerView)

        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            //dx > 0右滑，反之左滑，dy > 0 下滑，反之上滑
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mConsumeX = mConsumeX + dx
                mItemComusemX = mItemDecoration?.mItemComusemX!!
                // 移动一页理论消耗距离
                var shouldConsumeX = mItemComusemX
                // 获取当前的位置
                var position = getPosition(mConsumeX, shouldConsumeX.toInt())
                // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
                var offset = mConsumeX?.toFloat() / shouldConsumeX
                // 避免offset值取整时进一，从而影响了percent值
//                if (offset >= (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1 && slideDirct == SLIDE_RIGHT) {
                if (offset >= (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1) {
                    return
                }
                // 当前页的偏移率
                var percent = offset - offset?.toInt()
//                Log.e(TAG, ">>>>>>>percent:" + percent)
                // 设置动画变化
                setAnimation(recyclerView, position, percent)

            }
        })
    }

    var mAnimFactor = 0.4f//控制我们的图片从1.0伸缩至0.8

    private fun setAnimation(recyclerView: RecyclerView, position: Int, percent: Float) {
        var mCurView = recyclerView.getLayoutManager().findViewByPosition(position)     // 中间页
        var mRightView = recyclerView.getLayoutManager().findViewByPosition(position + 1) // 左边页
        var mLeftView = recyclerView.getLayoutManager().findViewByPosition(position - 1)  // 右边页

        if (percent <= 0.5) {//左滑
            if (mLeftView != null) {
                // 变大
                mLeftView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
                mLeftView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
            if (mCurView != null) {
                // 变小
                mCurView.setScaleX(1 - percent * mAnimFactor);
                mCurView.setScaleY(1 - percent * mAnimFactor);
            }
            if (mRightView != null) {
                // 变大
                mRightView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
                mRightView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
        } else {
            if (mLeftView != null) {
                mLeftView.setScaleX(1 - percent * mAnimFactor);
                mLeftView.setScaleY(1 - percent * mAnimFactor);
            }
            if (mCurView != null) {
                mCurView.setScaleX((1 - mAnimFactor) + percent * mAnimFactor);
                mCurView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
            if (mRightView != null) {
                mRightView.setScaleX(1 - percent * mAnimFactor);
                mRightView.setScaleY(1 - percent * mAnimFactor);
            }
        }

    }

    //RecyclerView滑动的总距离
    var mConsumeX = 0
    var mItemDecoration: GalleryItemDecoration? = null
    var mItemComusemX = 0f //一页理论消耗距离
    /**
     * 获取位置
     *
     * @param mConsumeX      实际消耗距离
     * @param shouldConsumeX 移动一页理论消耗距离
     * @return
     */
    fun getPosition(mConsumeX: Int, shouldConsumeX: Int): Int {
        //总的偏移率
        var offset = mConsumeX?.toFloat() / shouldConsumeX?.toFloat()
        var position = Math.round(offset)        // 四舍五入获取位置
        return position;
    }

}