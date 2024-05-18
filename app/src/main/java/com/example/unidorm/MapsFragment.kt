package com.example.unidorm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.unidorm.fragments.ChosenDormInfoFragment
import com.example.unidorm.fragments.contract.navigator
import com.example.unidorm.model.dbModel.DBHandler
import com.example.unidorm.model.dbModel.DormitoryModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import java.util.ArrayList

class MapsFragment : Fragment() {
    private lateinit var mapView: MapView
    private var dormList = mutableListOf<DormitoryModel>()
    private val dbHandl = DBHandler()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayListOf())
        dbHandl.readAddressInfo().observe(viewLifecycleOwner) {
            val items = ArrayList<String>()
            for (dorm in it) {
                if (dorm.dormName != null) {
                    dormList.add(dorm)
                }
                items.add(dorm.dormName.toString())
            }
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        }
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        // Инициализация карт
        MapKitFactory.initialize(requireContext())
        mapView = MapView(requireContext())

        mapView.map.move(
            CameraPosition(
                Point(56.484645, 84.947649),
                13.0f,
                0.0f,
                30.0f
            )
        )
        MapPoints()

        // Добавляем mapView в разметку фрагмента
        val mapViewContainer = view.findViewById<ViewGroup>(R.id.mapViewContainer)
        mapViewContainer.addView(mapView)

        return view
    }

    fun MapPoints() {
        val dorm3PlacemarkTapListener = createPlacemarkTapListener("Общежитие № 3")
        val dorm5PlacemarkTapListener = createPlacemarkTapListener("Общежитие № 5")
        val dorm7PlacemarkTapListener = createPlacemarkTapListener("Общежитие № 7")
        val dorm8PlacemarkTapListener = createPlacemarkTapListener("Общежитие № 8")
        val dormMayakPlacemarkTapListener =
            createPlacemarkTapListener("Студенческий жилой комплекс «Маяк»")
        val dormParusPlacemarkTapListener =
            createPlacemarkTapListener("Студенческий жилой комплекс «Парус»")

        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.marker_home_map)
        val dorm7 = mapView.map.mapObjects.addPlacemark(Point(56.452528, 84.972298), imageProvider)
        dorm7.addTapListener(dorm7PlacemarkTapListener)

        val dorm8 = mapView.map.mapObjects.addPlacemark(Point(56.451630, 84.972281), imageProvider)
        dorm8.addTapListener(dorm8PlacemarkTapListener)

        val dormMayak =
            mapView.map.mapObjects.addPlacemark(Point(56.462618, 84.933840), imageProvider)
        dormMayak.addTapListener(dormMayakPlacemarkTapListener)

        val dormParus =
            mapView.map.mapObjects.addPlacemark(Point(56.470527, 84.937070), imageProvider)
        dormParus.addTapListener(dormParusPlacemarkTapListener)

        val dorm5 = mapView.map.mapObjects.addPlacemark(Point(56.468317, 84.952218), imageProvider)
        dorm5.addTapListener(dorm5PlacemarkTapListener)

        val dorm3 = mapView.map.mapObjects.addPlacemark(Point(56.451697, 84.973542), imageProvider)
        dorm3.addTapListener(dorm3PlacemarkTapListener)

    }

    fun createPlacemarkTapListener(dormNameToFind: String): MapObjectTapListener {
        return MapObjectTapListener { _, point ->
            val fragment = ChosenDormInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable("dorm", dormList.find { it.dormName == dormNameToFind })
            fragment.arguments = bundle
            navigator().showItemInShopFragment(fragment)
            true
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}

