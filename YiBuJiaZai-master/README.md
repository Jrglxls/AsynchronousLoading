# YiBuJiaZai

###提高用户体验
使用缓存
###Lru算法
- Lru：Least Recently Used 近期最少使用算法
- Android提供了LruCashe类来实现这个缓存算法

###提高效率
- ListView滑动停止后才加载可见项
- ListView滑动时，取消所有加载项

###总结：
- 通过异步加载，避免阻塞UI线程
- 通过LruCache，将已下载图片放到内存中
- 通过判断ListView滑动状态，决定何时加载图片
- 不仅是ListView，任何控件都可以使用异步加载
