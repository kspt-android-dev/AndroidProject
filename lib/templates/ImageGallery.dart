import 'package:flutter/material.dart';
import 'package:polytable/data/CalendarData.dart';
import 'package:photo_view/photo_view_gallery.dart';
import 'package:url_launcher/url_launcher.dart';

class ThumbnailGallery extends StatelessWidget {

  final List<File> images;

  ThumbnailGallery({this.images});

  void open(BuildContext context, final String index) {
    Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => GalleryPhotoViewWrapper(
            backgroundDecoration: const BoxDecoration(
              color: Colors.black87,
            ),
            images: images.where((file) => file.image).map((file) => PhotoViewGalleryPageOptions(
              imageProvider: Image.network(file.url).image,
              heroTag: file.name,
            )).toList(),
            origins: images.map((file) => file.origin).toList(),
            strIndex: index,
          ),
        ));
  }

  void _launch(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child:
            Row(
                children: images
                  .map((file) => Container(
                    padding: EdgeInsets.only(left: 2, right: 2),
                    child: GestureDetector(
                      onTap: () {
                        if (file.image)
                          open(context, file.name);
                        else {
                          _launch(file.url);
                        }
                      },
                      child: (file.image) ? Hero(
                        tag: file.name,
                        child:  ClipRRect(
                          borderRadius: BorderRadius.circular(15.0),
                          child: Image.network(file.thumbnail)
                        )
                      ) : ClipRRect(
                          borderRadius: BorderRadius.circular(15.0),
                          child: Image.asset('assets/images/file.png')
                      )
                    )
                  )
                ).toList()
            )
    );
  }
}

class GalleryPhotoViewWrapper extends StatefulWidget {
  GalleryPhotoViewWrapper({
    this.images,
    this.loadingChild,
    this.backgroundDecoration,
    this.minScale,
    this.maxScale,
    this.strIndex,
    this.origins,
  }) : pageController = PageController(initialPage: images.indexWhere((image) => image.heroTag == strIndex));

  final List<PhotoViewGalleryPageOptions> images;
  final List<String> origins;
  final Widget loadingChild;
  final Decoration backgroundDecoration;
  final dynamic minScale;
  final dynamic maxScale;
  final String strIndex;
  final PageController pageController;

  @override
  State<StatefulWidget> createState() {
    return _GalleryPhotoViewWrapperState();
  }
}

class _GalleryPhotoViewWrapperState extends State<GalleryPhotoViewWrapper> {
  int currentIndex;
  @override
  void initState() {
    currentIndex = widget.pageController.initialPage;
    super.initState();
  }

  void onPageChanged(int index) {
    setState(() {
      currentIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
          constraints: BoxConstraints.expand(
            height: MediaQuery.of(context).size.height,
          ),
          child: Stack(
            alignment: Alignment.bottomRight,
            children: <Widget>[
              PhotoViewGallery(
                pageOptions: widget.images,
                loadingChild: widget.loadingChild,
                backgroundDecoration: widget.backgroundDecoration,
                pageController: widget.pageController,
                onPageChanged: onPageChanged,
              ),
              Container(
                padding: const EdgeInsets.all(20.0),
                child: Text(
                  widget.origins[currentIndex],
                  style: const TextStyle(
                      color: Colors.white, fontSize: 17.0, decoration: null),
                ),
              )
            ],
          )),
    );
  }
}